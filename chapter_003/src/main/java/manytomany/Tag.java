package manytomany;

import javax.persistence.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ManyToMany(mappedBy = "tags")
    private Set<Post> posts = new HashSet<>();

    static public Tag of(String name) {
        Tag tag = new Tag();
        tag.setName(name);
        return tag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tag tag = (Tag) o;
        return Objects.equals(id, tag.id)
                && Objects.equals(name, tag.name)
                && Objects.equals(posts, tag.posts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, posts);
    }
}
