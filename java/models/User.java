package models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 従業員データのDTOモデル
 *
 */
@Table(name = JpaConst.TABLE_USE)

@NamedQueries({
    @NamedQuery(
            name = JpaConst.Q_USE_COUNT_REGISTERED_BY_CODE,
            query = JpaConst.Q_USE_COUNT_REGISTERED_BY_CODE_DEF)
})

@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する
@NoArgsConstructor //引数なしのコンストラクタを自動生成する
@AllArgsConstructor //全てのクラスフィールドを引数に持つ引数ありコンストラクタを自動生成する
@Entity
public class User {

    /**
     * id
     */
    @Id
    @Column(name = JpaConst.USE_COL_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 会員番号
     */
    @Column(name = JpaConst.USE_COL_CODE, nullable = false, unique = true)
    private String code;

    /**
     * 氏名
     */
    @Column(name = JpaConst.USE_COL_NAME, nullable = false)
    private String name;

    /**
     * メールアドレス
     */
    @Column(name = JpaConst.USE_COL_MAIL, nullable = false)
    private String mail;

    /**
     * パスワード
     */
    @Column(name = JpaConst.USE_COL_PASS, length = 64, nullable = false)
    private String password;

    /**
     * 登録日時
     */
    @Column(name = JpaConst.USE_COL_CREATE_AT, nullable = false)
    private LocalDateTime createdAt;

    /**
     * 更新日時
     */
    @Column(name = JpaConst.USE_COL_UPDATE_AT, nullable = false)
    private LocalDateTime updatedAt;

}
