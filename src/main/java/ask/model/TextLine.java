package ask.model;

import javax.persistence.*;

@Entity
@Table(name = "text_line")
public class TextLine {
    @Id
    @Column(name = "upper_case_text")
    private String upperCaseLine;

    @Column(name = "text_line")
    private String line;

    public String getPrimaryKey() {
        return this.upperCaseLine;
    }

    public void setPrimaryKey(String uperCaseLine) {
        this.upperCaseLine = uperCaseLine;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public TextLine() {
    }

    public TextLine(String line) {
        this.line = line;
        this.upperCaseLine = line.toUpperCase();
    }
}
