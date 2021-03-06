package org.iilab.pb.model;

import android.content.Context;

import org.iilab.pb.BuildConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk=21)
public class SMSSettingsTest {

    @Test
    public void shouldSaveAndRetrieveSMSSettings() throws Exception {
        Context context = RuntimeEnvironment.application;

        List<String> expectedPhoneNumbers = new ArrayList<String>();
        expectedPhoneNumbers.add("123456789");
        expectedPhoneNumbers.add("987654321");
        String expectedMessage = "Test Message";
        String stopAlertMessage="Stop Alert";

        SMSSettings smsSettings = new SMSSettings(expectedPhoneNumbers, expectedMessage,stopAlertMessage);

        SMSSettings.saveContacts(context, smsSettings);
        SMSSettings.saveMessage(context, expectedMessage);
        SMSSettings retrievedSMSSettings = SMSSettings.retrieve(context);

        assertEquals(expectedMessage, retrievedSMSSettings.message());
        assertEquals(expectedPhoneNumbers.get(0), retrievedSMSSettings.phoneNumberAt(0));
        assertEquals(expectedPhoneNumbers.get(1), retrievedSMSSettings.phoneNumberAt(1));
    }

    @Test
    public void shouldReturnEmptyPhoneNumberForNonExistentIndex() {
        SMSSettings smsSettings = new SMSSettings(new ArrayList<String>(), "msg","stopAlert");
        assertEquals("", smsSettings.phoneNumberAt(1));
    }

    @Test
    public void shouldMaskPhoneNumberExceptTheLastTwoChars() {
        List<String> phoneNumbers = Arrays.asList("111-222-2289", "123456789", "*******89", "1", "22", "", null);
        SMSSettings smsSettings = new SMSSettings(phoneNumbers, "some message","stop Alert");

        assertEquals("**********89", smsSettings.maskedPhoneNumberAt(0));
        assertEquals("*******89", smsSettings.maskedPhoneNumberAt(1));
        assertEquals("*******89", smsSettings.maskedPhoneNumberAt(2));
        assertEquals("1", smsSettings.maskedPhoneNumberAt(3));
        assertEquals("22", smsSettings.maskedPhoneNumberAt(4));
        assertEquals("", smsSettings.maskedPhoneNumberAt(5));
        assertNull(smsSettings.maskedPhoneNumberAt(6));
    }

    @Test
    public void shouldReturnTrueWhenAnyOfThePhoneNumbersIsPresent() {
        List<String> phoneNumbers = new ArrayList<String>();
        phoneNumbers.add("123-123-1222");
        phoneNumbers.add("");
        SMSSettings smsSettings = new SMSSettings(phoneNumbers, "some-message","stop Alert");
        assertTrue(smsSettings.isConfigured());
    }

    @Test
    public void shouldReturnTrueWhenAnyOfThePhoneNumbersIsPresentEvenWhenMessageIsNull() {
        List<String> phoneNumbers = new ArrayList<String>();
        phoneNumbers.add("");
        phoneNumbers.add("123-123-1222");
        SMSSettings smsSettings = new SMSSettings(phoneNumbers, null,null);
        assertTrue(smsSettings.isConfigured());
    }

    @Test
    public void shouldReturnFalseWhenPhoneNumbersAreEmpty() {
        List<String> phoneNumbers = new ArrayList<String>();
        phoneNumbers.add("");
        SMSSettings smsSettings = new SMSSettings(phoneNumbers, "some-message","Stop Alert");
        assertFalse(smsSettings.isConfigured());
    }

    @Test
    public void shouldReturnFalseWhenPhoneNumbersAreNotPresent() {
        SMSSettings smsSettings = new SMSSettings(new ArrayList<String>(), "some-message","Stop Alert");
        assertFalse(smsSettings.isConfigured());
    }

    @Test
    public void shouldReturnFalseWhenPhoneNumbersAreNull() {
        SMSSettings smsSettings = new SMSSettings(null, "some-message","Stop Alert");
        assertFalse(smsSettings.isConfigured());
    }

    @Test
    public void shouldReturnValidPhoneNumbers() {
        List<String> phoneNumbers = Arrays.asList("1233244235", "", "154-345-345", null);
        SMSSettings smsSettings = new SMSSettings(phoneNumbers, "some-message","Stop Alert");

        List<String> validPhoneNumbers = smsSettings.validPhoneNumbers();

        assertEquals(2, validPhoneNumbers.size());
        assertEquals("1233244235", validPhoneNumbers.get(0));
        assertEquals("154-345-345", validPhoneNumbers.get(1));
    }

    @Test
    public void shouldReturnMessageWithoutTrailingDot() {
        String messageWithDot = "some message.";
        String messageWithoutDot = "some message";
        String messageWithSpaceAtEnd = "some message ";

        SMSSettings smsSettings = new SMSSettings(new ArrayList<String>(), messageWithDot,"Stop Alert");
        assertEquals("some message", smsSettings.trimmedMessage());

        smsSettings = new SMSSettings(new ArrayList<String>(), messageWithoutDot,"Stop Alert");
        assertEquals("some message", smsSettings.trimmedMessage());

        smsSettings = new SMSSettings(new ArrayList<String>(), messageWithSpaceAtEnd,"Stop Alert");
        assertEquals("some message", smsSettings.trimmedMessage());
    }

    @Test
    public void shouldReturnEmptyStringWhenMessageIsNull() {
        SMSSettings smsSettings = new SMSSettings(new ArrayList<String>(), null,"Stop Alert");
        assertEquals("", smsSettings.message());
    }

    @Test
    public void shouldCheckEquality() {
        SMSSettings settings1 = new SMSSettings(Arrays.asList("12345"), "some message","Stop Alert");
        SMSSettings settings2 = new SMSSettings(Arrays.asList("1234" + "5"), "some message","Stop Alert");
        SMSSettings settings3 = new SMSSettings(Arrays.asList("1234" + "5"), "some other message","Stop Alert");
        assertTrue(settings1.equals(settings2));
        assertFalse(settings1.equals(null));
        assertFalse(settings1.equals(settings3));
    }
}
