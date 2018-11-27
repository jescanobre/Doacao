package dsdm.ufc.doacao.managers;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

public class PermissionManager {

    public static boolean checkPermission(Activity activity, String PERMISSION) {
        return ContextCompat.checkSelfPermission(activity, PERMISSION) == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermission(Activity activity, String PERMISSION, int requestCode, String title, String message) {
        if(ActivityCompat.shouldShowRequestPermissionRationale(activity, PERMISSION) &&
           title != null && message != null && !message.isEmpty()) {
            AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
            alertDialog.setTitle(title);
            alertDialog.setMessage(message);
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{PERMISSION}, requestCode);
        }
    }
}
