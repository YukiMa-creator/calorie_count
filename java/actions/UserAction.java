package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.UserView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.MessageConst;
import constants.PropertyConst;
import services.UserService;

/**
 *従業員に関わる処理を行うActionクラス
 *
 */
public class UserAction extends ActionBase {

    private UserService service;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new UserService();

        //メソッドを実行
        invoke();

        service.close();
    }

    /**
     * 新規登録画面を表示する
     * @throws ServletException
     * @throws IOException
     */
public void entryNew() throws ServletException, IOException {

    putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSPF対策用トークン
    putRequestScope(AttributeConst.USER, new UserView()); //空のユーザーインスタンス

    //新規登録画面を表示
    forward(ForwardConst.FW_USE_NEW);
}

/**
 * 新規登録を行う
 * @throws ServletException
 * @throws IOException
 */
public void create() throws ServletException, IOException {

    //CSRF対策 tokenチェック
    if(checkToken()) {

        //パラメータの値を元に会員情報のインスタンスを作成
        UserView uv = new UserView(
                null,
                getRequestParam(AttributeConst.USE_CODE),
                getRequestParam(AttributeConst.USE_NAME),
                getRequestParam(AttributeConst.USE_MAIL),
                getRequestParam(AttributeConst.USE_PASS),
                null,
                null);

        //アプリケーションスコープからpepper文字列を取得
        String pepper = getContextScope(PropertyConst.PEPPER);

        //会員情報登録
        List<String> errors = service.create(uv, pepper);

        if(errors.size() > 0) {
            //登録中にエラーがあった場合

            putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
            putRequestScope(AttributeConst.USER, uv); //入力された会員情報
            putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

            //新規登録画面を再表示
            forward(ForwardConst.FW_USE_NEW);

        } else {
            //登録中にエラーがなかった場合

            //セッションに登録完了のフラッシュメッセージを設定
            putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());

            //一覧画面にリダイレクト
            redirect(ForwardConst.ACT_AUTH,ForwardConst.CMD_INDEX);
        }
    }
}
}
