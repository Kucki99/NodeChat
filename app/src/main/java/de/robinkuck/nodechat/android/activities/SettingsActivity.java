package de.robinkuck.nodechat.android.activities;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import de.robinkuck.nodechat.android.ChangeNickDialog;
import de.robinkuck.nodechat.android.R;
import de.robinkuck.nodechat.android.managers.NickManager;
import de.robinkuck.nodechat.android.managers.SocketManager;

public class SettingsActivity extends AbstractChildActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        super.configViews();
    }

    public void onNickChange(View v) {
        System.out.println("[I] Try to change nick!");
        final ChangeNickDialog changeNickDialog = new ChangeNickDialog();
        changeNickDialog.show(getFragmentManager(), "ChangeNickFragment");
    }

}
