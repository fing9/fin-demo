package fin.finmemo.domain;

import javax.persistence.*;

@Entity
public class Member {

    //Id:pk값 GeneratedValue:아이덴티티 전략 (DB에서 알아서 고윳값을 생성해주는 것)
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
