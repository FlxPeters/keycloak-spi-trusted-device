package nl.wouterh.keycloak.trusteddevice.credential;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.keycloak.credential.CredentialModel;
import org.keycloak.util.JsonSerialization;

public class TrustedDeviceCredentialModelTest {

    @Test
    public void testCreateATrustedDeviceCredentialModel() {

        String credentialName = "my-credential";
        String deviceId = "random-device-id";
        long exp = 100;

        TrustedDeviceCredentialModel trustedDeviceCredentialModel = TrustedDeviceCredentialModel.create(
                credentialName, deviceId, exp);

        assertEquals(credentialName, trustedDeviceCredentialModel.getUserLabel());
        assertEquals(deviceId, trustedDeviceCredentialModel.getDeviceId());
        assertEquals(exp, trustedDeviceCredentialModel.getExpireTime());   
        assertEquals(TrustedDeviceCredentialModel.TYPE_TWOFACTOR, trustedDeviceCredentialModel.getType());
    
    }

    @Test
    // Test casting from generic credential model to trusted device credential model
    public void testCreateFromCredentialModel() throws IOException {

        String deviceId = "random-device-id";
        long exp = 100;

        TrustedDeviceCredentialData data = new TrustedDeviceCredentialData(exp);
        TrustedDeviceSecretData secret = new TrustedDeviceSecretData(deviceId);

        CredentialModel cm = new CredentialModel();

        cm.setCredentialData(JsonSerialization.writeValueAsString(data));
        cm.setSecretData(JsonSerialization.writeValueAsString(secret));


        TrustedDeviceCredentialModel trustedDeviceCredentialModel =
            TrustedDeviceCredentialModel.createFromCredentialModel(cm);
        assertEquals(deviceId, trustedDeviceCredentialModel.getDeviceId());
        assertEquals(exp, trustedDeviceCredentialModel.getExpireTime());
    }
}
