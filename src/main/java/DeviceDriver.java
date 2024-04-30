/**
 * This class is used by the operating system to interact with the hardware 'FlashMemoryDevice'.
 */
public class DeviceDriver {
    FlashMemoryDevice hardware;

    public DeviceDriver(FlashMemoryDevice hardware) {
        this.hardware = hardware;
    }

    public byte read(long address) throws Exception {
        byte data = hardware.read(address);

        for (int i = 1; i < 5; i++) {
            if (data == hardware.read(address)) {
                continue;
            }
            throw new ReadFailException("Cannot read");
        }

        return data;

    }

    public class ReadFailException extends Exception {
        public ReadFailException(String message) {
            super(message);
        }
    }

    public void write(long address, byte data) throws Exception {
        if (hardware.read(address) == (byte) 0XFF) {
            hardware.write(address, data);
        } else {
            throw new WriteFailException("Cannot write ");
        }

    }

    public class WriteFailException extends Exception {
        public WriteFailException(String message) {
            super(message);
        }
    }
}