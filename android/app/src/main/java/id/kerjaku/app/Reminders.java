package id.kerjaku.app;

import android.Manifest;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Pengingat yang tetap muncul walau aplikasi ditutup.
 * Aplikasi web mengirim daftar pengingat (JSON); setiap item dijadwalkan dengan AlarmManager.
 * Daftar disimpan agar bisa dijadwalkan ulang setelah HP dinyalakan ulang.
 */
final class Reminders {

    static final String CHANNEL_ID = "pengingat";
    private static final String PREFS = "kerjaku_reminders";
    private static final String KEY_LIST = "list";
    private static final String KEY_CODES = "codes";

    private Reminders() { }

    static void createChannel(Context ctx) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel ch = new NotificationChannel(CHANNEL_ID, "Pengingat To Do", NotificationManager.IMPORTANCE_HIGH);
            ch.setDescription("Pengingat saat jam To Do dan deadline tugas tiba");
            ctx.getSystemService(NotificationManager.class).createNotificationChannel(ch);
        }
    }

    /** Ganti semua alarm dengan daftar baru. json: [{key, at, title, body}] */
    static void schedule(Context ctx, String json) {
        SharedPreferences sp = ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        AlarmManager am = ctx.getSystemService(AlarmManager.class);

        // Batalkan alarm lama.
        try {
            JSONArray old = new JSONArray(sp.getString(KEY_CODES, "[]"));
            for (int i = 0; i < old.length(); i++) am.cancel(pending(ctx, old.getInt(i), null));
        } catch (Exception ignored) { }

        JSONArray codes = new JSONArray();
        long now = System.currentTimeMillis();
        try {
            JSONArray list = new JSONArray(json);
            for (int i = 0; i < list.length(); i++) {
                JSONObject r = list.getJSONObject(i);
                long at = r.getLong("at");
                if (at <= now) continue;
                int code = r.getString("key").hashCode();
                PendingIntent pi = pending(ctx, code, r);
                boolean exact = Build.VERSION.SDK_INT < Build.VERSION_CODES.S || am.canScheduleExactAlarms();
                if (exact) am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, at, pi);
                else am.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, at, pi);
                codes.put(code);
            }
        } catch (Exception ignored) { }

        sp.edit().putString(KEY_LIST, json).putString(KEY_CODES, codes.toString()).apply();
    }

    /** Dipanggil setelah HP dinyalakan ulang atau aplikasi diperbarui. */
    static void reschedule(Context ctx) {
        String json = ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getString(KEY_LIST, "[]");
        schedule(ctx, json);
    }

    private static PendingIntent pending(Context ctx, int code, JSONObject r) {
        Intent i = new Intent(ctx, ReminderReceiver.class).setAction("id.kerjaku.app.REMIND");
        if (r != null) {
            i.putExtra("title", r.optString("title"));
            i.putExtra("body", r.optString("body"));
            i.putExtra("id", code);
        }
        return PendingIntent.getBroadcast(ctx, code, i, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
    }

    static void show(Context ctx, int id, String title, String body) {
        if (Build.VERSION.SDK_INT >= 33
                && ctx.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        createChannel(ctx);
        Intent open = new Intent(ctx, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pi = PendingIntent.getActivity(ctx, 0, open, PendingIntent.FLAG_IMMUTABLE);
        Notification.Builder b = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
                ? new Notification.Builder(ctx, CHANNEL_ID)
                : new Notification.Builder(ctx);
        b.setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setContentIntent(pi);
        ctx.getSystemService(NotificationManager.class).notify(id, b.build());
    }
}
