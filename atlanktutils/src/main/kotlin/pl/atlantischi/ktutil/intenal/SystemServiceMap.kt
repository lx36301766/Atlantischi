package pl.atlantischi.ktutil.intenal

import android.accounts.AccountManager
import android.app.*
import android.app.admin.DevicePolicyManager
import android.app.job.JobScheduler
import android.app.usage.UsageStatsManager
import android.appwidget.AppWidgetManager
import android.bluetooth.BluetoothManager
import android.content.ClipboardManager
import android.content.Context
import android.content.RestrictionsManager
import android.content.pm.LauncherApps
import android.hardware.ConsumerIrManager
import android.hardware.SensorManager
import android.hardware.camera2.CameraManager
import android.hardware.display.DisplayManager
import android.hardware.input.InputManager
import android.hardware.usb.UsbManager
import android.location.LocationManager
import android.media.AudioManager
import android.media.MediaRouter
import android.media.projection.MediaProjectionManager
import android.media.session.MediaSessionManager
import android.media.tv.TvInputManager
import android.net.ConnectivityManager
import android.net.nsd.NsdManager
import android.net.wifi.WifiManager
import android.net.wifi.p2p.WifiP2pManager
import android.nfc.NfcManager
import android.os.*
import android.os.storage.StorageManager
import android.print.PrintManager
import android.support.annotation.RequiresApi
import android.telecom.TelecomManager
import android.telephony.SubscriptionManager
import android.telephony.TelephonyManager
import android.view.LayoutInflater
import android.view.WindowManager
import android.view.accessibility.AccessibilityManager
import android.view.accessibility.CaptioningManager
import android.view.inputmethod.InputMethodManager
import android.view.textservice.TextServicesManager

/**
 * Created on 12/09/2018.

 * @author lx
 *
 * @see android.app.SystemServiceRegistry
 *
 */

internal object SystemServiceMap {

    val SERVICE_NAMES = hashMapOf<Class<*>, String>()

    init {
        SERVICE_NAMES[AccessibilityManager::class.java] = Context.ACCESSIBILITY_SERVICE
        SERVICE_NAMES[CaptioningManager::class.java] = Context.CAPTIONING_SERVICE
        SERVICE_NAMES[AccountManager::class.java] = Context.ACCOUNT_SERVICE
        SERVICE_NAMES[ActivityManager::class.java] = Context.ACTIVITY_SERVICE
        SERVICE_NAMES[AlarmManager::class.java] = Context.ALARM_SERVICE
        SERVICE_NAMES[AudioManager::class.java] = Context.AUDIO_SERVICE
        SERVICE_NAMES[MediaRouter::class.java] = Context.MEDIA_ROUTER_SERVICE
        SERVICE_NAMES[BluetoothManager::class.java] = Context.BLUETOOTH_SERVICE
//        SERVICE_NAMES[TextClassificationManager::class.java] = Context.TEXT_CLASSIFICATION_SERVICE // api 26
        SERVICE_NAMES[ClipboardManager::class.java] = Context.CLIPBOARD_SERVICE
        SERVICE_NAMES[ConnectivityManager::class.java] = Context.CONNECTIVITY_SERVICE
        SERVICE_NAMES[DevicePolicyManager::class.java] = Context.DEVICE_POLICY_SERVICE
        SERVICE_NAMES[DownloadManager::class.java] = Context.DOWNLOAD_SERVICE
        SERVICE_NAMES[BatteryManager::class.java] = "batterymanager" //Context.BATTERY_SERVICE --- api 21
        SERVICE_NAMES[NfcManager::class.java] = Context.NFC_SERVICE
        SERVICE_NAMES[DropBoxManager::class.java] = Context.DROPBOX_SERVICE
        SERVICE_NAMES[InputManager::class.java] = Context.INPUT_SERVICE
        SERVICE_NAMES[DisplayManager::class.java] = Context.DISPLAY_SERVICE
        SERVICE_NAMES[InputMethodManager::class.java] = Context.INPUT_METHOD_SERVICE
        SERVICE_NAMES[TextServicesManager::class.java] = Context.TEXT_SERVICES_MANAGER_SERVICE
        SERVICE_NAMES[KeyguardManager::class.java] = Context.KEYGUARD_SERVICE
        SERVICE_NAMES[LayoutInflater::class.java] = Context.LAYOUT_INFLATER_SERVICE
        SERVICE_NAMES[LocationManager::class.java] = Context.LOCATION_SERVICE
        SERVICE_NAMES[NotificationManager::class.java] = Context.NOTIFICATION_SERVICE
        SERVICE_NAMES[NsdManager::class.java] = Context.NSD_SERVICE
        SERVICE_NAMES[PowerManager::class.java] = Context.POWER_SERVICE
//        SERVICE_NAMES[RecoverySystem::class.java] = "recovery" //Context.RECOVERY_SERVICE ---hide
        SERVICE_NAMES[SearchManager::class.java] = Context.SEARCH_SERVICE
        SERVICE_NAMES[SensorManager::class.java] = Context.SENSOR_SERVICE
        SERVICE_NAMES[StorageManager::class.java] = Context.STORAGE_SERVICE
//        SERVICE_NAMES[StorageStatsManager::class.java] = Context.STORAGE_STATS_SERVICE // api 26
        SERVICE_NAMES[TelephonyManager::class.java] = Context.TELEPHONY_SERVICE
        @RequiresApi(22)
        SERVICE_NAMES[SubscriptionManager::class.java] = Context.TELEPHONY_SUBSCRIPTION_SERVICE
//        SERVICE_NAMES[CarrierConfigManager::class.java] = Context.CARRIER_CONFIG_SERVICE // api 23
        @RequiresApi(21)
        SERVICE_NAMES[TelecomManager::class.java] = Context.TELECOM_SERVICE
        SERVICE_NAMES[UiModeManager::class.java] = Context.UI_MODE_SERVICE
        SERVICE_NAMES[UsbManager::class.java] = Context.USB_SERVICE
        SERVICE_NAMES[Vibrator::class.java] = Context.VIBRATOR_SERVICE
        SERVICE_NAMES[WifiManager::class.java] = Context.WIFI_SERVICE
        SERVICE_NAMES[WifiP2pManager::class.java] = Context.WIFI_P2P_SERVICE
//        SERVICE_NAMES[WifiAwareManager::class.java] = Context.WIFI_AWARE_SERVICE //api 26
        SERVICE_NAMES[WindowManager::class.java] = Context.WINDOW_SERVICE
        SERVICE_NAMES[AppOpsManager::class.java] = Context.APP_OPS_SERVICE
        @RequiresApi(21)
        SERVICE_NAMES[CameraManager::class.java] = Context.CAMERA_SERVICE
        @RequiresApi(21)
        SERVICE_NAMES[LauncherApps::class.java] = Context.LAUNCHER_APPS_SERVICE
        @RequiresApi(21)
        SERVICE_NAMES[RestrictionsManager::class.java] = Context.RESTRICTIONS_SERVICE
        SERVICE_NAMES[PrintManager::class.java] = Context.PRINT_SERVICE
//        SERVICE_NAMES[CompanionDeviceManager::class.java] = Context.COMPANION_DEVICE_SERVICE //api 26
        SERVICE_NAMES[ConsumerIrManager::class.java] = Context.CONSUMER_IR_SERVICE
        @RequiresApi(21)
        SERVICE_NAMES[MediaSessionManager::class.java] = Context.MEDIA_SESSION_SERVICE
//        SERVICE_NAMES[FingerprintManager::class.java] = Context.FINGERPRINT_SERVICE //api 23
        @RequiresApi(21)
        SERVICE_NAMES[TvInputManager::class.java] = Context.TV_INPUT_SERVICE
        @RequiresApi(21)
        SERVICE_NAMES[UsageStatsManager::class.java] = "usagestats" //Context.USAGE_STATS_SERVICE -- api 22
//        SERVICE_NAMES[NetworkStatsManager::class.java] = Context.NETWORK_STATS_SERVICE //api 23
        @RequiresApi(21)
        SERVICE_NAMES[JobScheduler::class.java] = Context.JOB_SCHEDULER_SERVICE
        @RequiresApi(21)
        SERVICE_NAMES[MediaProjectionManager::class.java] = Context.MEDIA_PROJECTION_SERVICE
        SERVICE_NAMES[AppWidgetManager::class.java] = "appwidget" //Context.APPWIDGET_SERVICE -- api 21
//        SERVICE_NAMES[MidiManager::class.java] = Context.MIDI_SERVICE //api 23
//        SERVICE_NAMES[HardwarePropertiesManager::class.java] = Context.HARDWARE_PROPERTIES_SERVICE //api 24
//        SERVICE_NAMES[ShortcutManager::class.java] = Context.SHORTCUT_SERVICE //api 25
//        SERVICE_NAMES[SystemHealthManager::class.java] = Context.SYSTEM_HEALTH_SERVICE //api 24
//        SERVICE_NAMES[AutofillManager::class.java] = "autofill" //Context.AUTOFILL_MANAGER_SERVICE api 26
    }

}
