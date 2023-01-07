package actions;

import java.io.IOException;

import javax.servlet.ServletException;

import actions.views.UserView;
import constants.AttributeConst;
import constants.ForwardConst;
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
}
