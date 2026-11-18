package id.kerjaku.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/** Menampilkan notifikasi saat alarm tiba, dan menjadwalkan ulang setelah HP restart. */
public class ReminderReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context ctx, Intent intent) {
        String action = intent.getAction();
        if (Intent.ACTION_BOOT_COMPLETED.equals(action) || Intent.ACTION_MY_PACKAGE_REPLACED.equals(action)) {
            Reminders.reschedule(ctx);
            return;
        }
        Reminders.show(ctx, intent.getIntExtra("id", 1), intent.getStringExtra("title"), intent.getStringExtra("body"));
    }
}
