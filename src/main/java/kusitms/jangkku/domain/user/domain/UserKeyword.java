package kusitms.jangkku.domain.user.domain;

import jakarta.persistence.*;
import kusitms.jangkku.domain.keyword.domain.Keyword;
import kusitms.jangkku.global.common.dao.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "users_keywords")
public class UserKeyword extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", foreignKey = @ForeignKey(name = "users_keywords_fk_users_id"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "keywords_id", foreignKey = @ForeignKey(name = "users_keywords_fk_keywords_id"))
    private Keyword keyword;

    @Builder
    public UserKeyword(User user, Keyword keyword) {
        this.user = user;
        this.keyword = keyword;
    }
}