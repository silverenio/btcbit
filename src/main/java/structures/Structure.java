package structures;

import lombok.Data;

@Data
public class Structure {

    private String webHost;
    private String user;
    private String password;

    Structure(String webHost, String user, String password) {
        this.webHost = webHost;
        this.user = user;
        this.password = password;
    }
}
