package actions;

import java.io.IOException;

import javax.servlet.ServletException;

import actions.views.UserView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.MessageConst;
import constants.PropertyConst;
import services.UserService;

/**
 * 認証に関する処理を行うActionクラス
 */
public class AuthAction extends ActionBase {

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
     * ログイン画面を表示する
     * @throws ServletException
     * @throws IOException
     *
     */
    public void showLogin() throws ServletException, IOException{

        //CSRF対策用トークンを設定
        putRequestScope(AttributeConst.TOKEN, getTokenId());

        //セッションにフラッシュメッセージが登録されている場合はリクエストスコープに設定する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if(flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        //ログイン画面を表示する
        forward(ForwardConst.FW_LOGIN);
    }

    public void login() throws ServletException, IOException{

        String code = getRequestParam(AttributeConst.USE_CODE);
        String plainPass = getRequestParam(AttributeConst.USE_PASS);
        String pepper = getContextScope(PropertyConst.PEPPER);

        //有効な会員か認証する
        Boolean isValidUser = service.validateLogin(code,  plainPass, pepper);

        if(isValidUser) {
            //認証成功の場合

            //CSRF対策 tokenのチェック
            if(checkToken()) {

                //ログインした会員のDBデータを取得
                UserView uv = service.findOne(code, plainPass, pepper);
                //セッションにログインした会員を設定
                putSessionScope(AttributeConst.LOGIN_USE, uv);
                //セッションログイン完了のフラッシュメッセージを設定する
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_LOGINED.getMessage());
                //トップページへリダイレクト
                redirect(ForwardConst.ACT_TOP, ForwardConst.CMD_INDEX);
            }
        } else {
            //認証失敗の場合

            //CSRF対策用トークンを設定
            putRequestScope(AttributeConst.TOKEN, getTokenId());
            //認証失敗エラーメッセージ表示フラグをたてる
            putRequestScope(AttributeConst.LOGIN_ERR, true);
            //入力された会員コードを設定
            putRequestScope(AttributeConst.USE_CODE, code);

            //ログイン画面を表示
            forward(ForwardConst.FW_LOGIN);
        }
    }

    /**
     * ログアウト処理を行う
     * @throws ServletException
     * @throws IOException
     */
    public void logout() throws ServletException,IOException {

        //セッションからログイン従業員のパラメータを削除
        removeSessionScope(AttributeConst.LOGIN_USE);

        //セッションにログアウト時のフラッシュメッセージを追加
        putSessionScope(AttributeConst.FLUSH, MessageConst.I_LOGOUT.getMessage());

        //ログイン画面にリダイレクト
        redirect(ForwardConst.ACT_AUTH, ForwardConst.CMD_SHOW_LOGIN);
    }

}
