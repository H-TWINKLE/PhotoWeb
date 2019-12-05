package com.ccc.common.controller;

import com.ccc.common.base.BaseController;
import com.ccc.common.interceptor.UserInterceptor;
import com.ccc.common.model.Post;
import com.ccc.common.model.User;
import com.ccc.common.service.CommService;
import com.ccc.common.validator.GobalValidator;
import com.ccc.utils.Utils;
import com.jfinal.aop.Before;
import com.jfinal.aop.Inject;
import com.jfinal.kit.StrKit;

import java.util.Date;

@Before(UserInterceptor.class)
public class UserController extends BaseController {

    @Inject
    CommService cService;

    public void index(Integer pages) {

        setTitle("个人中心");

        pages = getDefaultPages(pages);

        setAttr("pages", pages);

        setAttr("list", cService.getPostListByPostInUser(pages, 20, getUser()));

        render("user.html");

    }

    @Before(GobalValidator.class)
    public void addpost(String title, String message) {

        Post p = new Post();

        User u = getUser();

        p.setPostAuthor(u.getUserId());
        p.setPostTitle(title);
        p.setPostContent(message);
        p.setPostPlate(Utils.PLATEINPOST);
        p.setPostDates(new Date());
        p.setPostView(Utils.ISVERI);

        String[] pic = getParaValues("pic");

        if (pic != null) {

            StringBuffer s = new StringBuffer();

            for (int x = 0; x < pic.length; x++) {

                String path = Utils.INSTANCE.getFilePath(u);

                if (Utils.INSTANCE.base64ToImage(pic[x], path)) {

                    if (p.getPostPic() == null) {
                        p.setPostPic(path);
                    }

                    s.append(path);


                }

                if (x < pic.length - 1) {
                    s.append(",");
                }

            }

            p.setPostMpic(s.toString());

        }

        if (cService.save(p)) {
            setTipMsg("发送成功！");
        } else {
            setTipMsg("发送失败！");
        }

        forwardAction("/user");

    }

    public void info() {

        setTitle("个人信息");

        setAttr("user", getUser());

        render("userinfo.html");

    }

    public void modifyuser() {

        User u = getModel(User.class);

        u.setUserDates(new Date());

        String cronimg = getPara("cronimg");

        if (StrKit.notBlank(cronimg)) {

            String filepath = Utils.INSTANCE.getFilePath(u);

            if (Utils.INSTANCE.base64ToImage(cronimg, filepath)) {

                u.setUserPic(filepath);
            }

        }

        u = cService.saveUserAndReturnUpdate(u);

        if (u != null) {
            setTipMsg("修改成功!");
            setUser(u);
        } else {
            setTipMsg("修改失败！");
        }

        forwardAction("/user/info");

    }

    public void myc(Integer pages) {

        setTitle("我的评论");

        pages = getDefaultPages(pages);

        setPages(pages);

        setAttr("list", cService.getCommentByUser(pages, getUser()));

        render("myc.html");

    }

    public void mynews(Integer pages) {

        setTitle("我的消息");

        pages = getDefaultPages(pages);

        setPages(pages);

        setAttr("list", cService.getCommentByPostAuthor(pages, getUser()));

        render("mynews.html");

    }

}
