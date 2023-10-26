package G3.jio.exceptions;

public class ImageNotFoundException extends RuntimeException{
    public ImageNotFoundException() {
        super();
    }

    public ImageNotFoundException(Long Id) {
        super("Image Not Found: " + Id);
    }

    public ImageNotFoundException(String name) {
        super("Image Not Found: " + name);
    }

}
