package diy.czarja.pingponghelper;

import android.os.Bundle;

import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // Load the preferences from an XML resource
        setPreferencesFromResource(R.xml.pingpong_preference, rootKey);
        EditTextPreference etp = findPreference("dir_text");
        String dir = this.getActivity().getExternalFilesDir(null).toString() + "/test.mp4";
        etp.setDefaultValue(dir);
        etp.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                EditTextPreference etp = (EditTextPreference) preference;
                etp.setSummary(etp.getText());
                return true;
            }
        });
        etp.setText(dir);
        etp.setSummary(dir);
    }
}
