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

        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン

        //会員の空インスタンス
        UserView uv = new UserView();
        putRequestScope(AttributeConst.USER, uv); //日付のみ設定済みの日報インスタンス

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
        if (checkToken()) {

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

            if (errors.size() > 0) {
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
                redirect(ForwardConst.ACT_AUTH, ForwardConst.CMD_SHOW_LOGIN);
            }
        }
    }

    /**
     * 詳細画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void show() throws ServletException, IOException {

        //idを条件に従業員データを取得する
        UserView uv = service.findOne(toNumber(getRequestParam(AttributeConst.USE_ID)));

        if (uv == null) {

            //データが取得できなかった場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);
            return;
        }

        putRequestScope(AttributeConst.LOGIN_USE, uv); //取得した会員情報

        //詳細画面を表示
        forward(ForwardConst.FW_USE_SHOW);
    }

    public void edit() throws ServletException, IOException {

        //idを条件にユーザーデータを取得する
        UserView uv = service.findOne(toNumber(getRequestParam(AttributeConst.USE_ID)));

        if (uv == null) {

            //データが取得できなかった場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);
            return;
        }

        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
        putRequestScope(AttributeConst.USER, uv); //取得した会員情報

        //編集画面を表示する
        forward(ForwardConst.FW_USE_EDIT);

    }

    /**
     * 更新を行う
     * @throws ServletException
     * @throws IOException
     */
    public void update() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken()) {
            //パラメータの値を元に会員情報のインスタンスを作成する
            UserView uv = new UserView(
                    toNumber(getRequestParam(AttributeConst.USE_ID)),
                    getRequestParam(AttributeConst.USE_CODE),
                    getRequestParam(AttributeConst.USE_NAME),
                    getRequestParam(AttributeConst.USE_MAIL),
                    getRequestParam(AttributeConst.USE_PASS),
                    null,
                    null);

            //アプリケーションスコープからpepper文字列を取得
            String pepper = getContextScope(PropertyConst.PEPPER);

            //会員情報更新
            List<String> errors = service.update(uv, pepper);

            if (errors.size() > 0) {
                //更新中にエラーが発生した場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.USER, uv); //入力された会員情報
                putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                //編集画面を再表示
                forward(ForwardConst.FW_USE_EDIT);
            } else {
                //更新中にエラーがなかった場合

                //セッションに更新完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_UPDATED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_TOP, ForwardConst.CMD_INDEX);

            }

        }
    }

    /**
     *削除する
     */
    public void destroy() throws ServletException, IOException {

        UserView uv = getSessionScope(AttributeConst.LOGIN_USE);
        service.destroy(uv);

        putSessionScope(AttributeConst.FLUSH, MessageConst.I_DELETED.getMessage());

        removeSessionScope(AttributeConst.LOGIN_USE);

        //一覧画面にリダイレクト
        redirect(ForwardConst.ACT_AUTH, ForwardConst.CMD_SHOW_LOGIN);


    }

}
