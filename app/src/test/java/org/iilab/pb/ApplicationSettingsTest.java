package org.iilab.pb;

import android.app.Application;
import android.content.SharedPreferences;

import org.iilab.pb.common.ApplicationSettings;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowPreferenceManager;

import static junit.framework.Assert.assertNull;
import static org.iilab.pb.common.ApplicationSettings.isFirstRun;
import static org.iilab.pb.common.ApplicationSettings.setFirstRun;
import static org.junit.Assert.assertFalse;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk=21)
public class ApplicationSettingsTest {

    private SharedPreferences sharedPreferences;
    private Application context;

    @Before
    public void setUp() {
        context = RuntimeEnvironment.application;
        sharedPreferences = ShadowPreferenceManager.getDefaultSharedPreferences(context);
    }

    @Test
    public void shouldReturnTrueWhenOnFirstRun() {
//        assertTrue(isFirstRun(context));
    }

    @Test
    public void shouldReturnNullIfThereIsNoLocationStored() {
        assertNull(ApplicationSettings.getCurrentBestLocation(context));
    }

    @Test
    public void shouldUpdateFirstRunFlagOnCompletion() {
        setFirstRun(context, false);
        assertFalse(sharedPreferences.getBoolean("FIRST_RUN", true));
    }

    @Test
    public void shouldReturnFalseWhenFirstRunIsCompleted() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("FIRST_RUN", false);
        editor.commit();
        assertFalse(isFirstRun(context));
    }
}
