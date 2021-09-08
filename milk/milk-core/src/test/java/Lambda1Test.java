import entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class Lambda1Test {
    public static void main(String[] args) {

        //两个参数的用法
        List<User> userList = new ArrayList<>();
        userList.add(new User(1,"小龙女",18));
        userList.add(new User(2,"裘千仞",50));
        userList.add(new User(3,"郭襄",18));

        //将userList转化为key为id，value为User对象的map
        Map<Integer,User> lambadMap = userList.stream().collect(Collectors.toMap(User::getId,p->p));
        System.out.println(lambadMap);

        //User::getId ===》 User对象的getId方法
        //p -> p ===》就是进来的是什么，最终就是什么，这里就是进来的是User对象，出去的也就是User对象


        //三个参数的问题
        //如果这个时候你想获取key是age，value是name的map呢？如果你还是沿用上面的方法，就会出问题了，因为有两个age是 18 的数据，
        //也就是存在重复的key，会直接报错，想不报错的话，就可以利用第三个参数了。
        Map<Integer, String> map = userList.stream().collect(Collectors.toMap(User::getAge, User::getName, (a, b) -> b));
        System.out.println(map);

        // (a, b) -> b的意思就是，如果存在重复的，永远取后面一个
    }
}
