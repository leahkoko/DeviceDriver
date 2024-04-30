import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyByte;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeviceDriverTest {


    @Mock(answer = Answers.RETURNS_MOCKS)
    FlashMemoryDevice flashMemoryDevice;


    @Test
    public void read_From_Hardware_Success() throws Exception {
        DeviceDriver driver = new DeviceDriver(flashMemoryDevice);


        when(flashMemoryDevice.read(0XFF)).thenReturn((byte) 0);

        byte data = driver.read(0XFF);

        assertEquals(0, data);
    }

    @Test
    public void read_From_Hardware_Fail() throws Exception {
        DeviceDriver driver = new DeviceDriver(flashMemoryDevice);

        when(flashMemoryDevice.read(0XFF)).thenReturn((byte) 0)
                .thenReturn((byte) 1);

        assertThrows(DeviceDriver.ReadFailException.class, () -> {
            byte data = driver.read(0XFF);
        });

    }

    @Test
    public void write_Success() throws Exception {
        DeviceDriver driver = new DeviceDriver(flashMemoryDevice);

        when(flashMemoryDevice.read(0XFF)).thenReturn((byte) 0xFF);

        doNothing().when(flashMemoryDevice).write(anyLong(), anyByte());

        driver.write(0xFF, (byte) 1);
    }

    @Test
    public void write_Fail() throws Exception {
        DeviceDriver driver = new DeviceDriver(flashMemoryDevice);

        when(flashMemoryDevice.read(0XFF)).thenReturn((byte) 1);

        assertThrows(DeviceDriver.WriteFailException.class, () -> {
            driver.write(0xFF, (byte) 1);
        });
    }

}