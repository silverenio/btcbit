package structures;

import lombok.Getter;

public enum StructureSettings {

    WEB_HOST(new Structure("web.host", "web.user", "web.password"));

    @Getter
    private Structure structure;

    StructureSettings(Structure structure) {
        this.structure = structure;
    }
}
