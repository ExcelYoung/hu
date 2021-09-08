import java.util.ArrayList;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

public class LambdaTest {
    // 左边:参数 -> 右边:函数体 parameter -> expression body

    public static void main(String[] args) {

//        //【1】简单示例
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("我是传统的写法");
//            }
//        }).start();
//
//
////------------------------------------------------------------------------
//        new Thread(() -> System.out.println("我是lambda的写法")).start();


        //【2】foreach遍历输出 list.for增强for循环的快捷键
        List<String> list = new ArrayList<String>();
        list.add("apple");
        list.add("banana");
        list.add("peach");
        System.out.println("传统输出方式：");
        for (String s : list) {
            System.out.println(s);
        }
 //------------------------------------------------------------------------
        System.out.println("lambda输出方式：");
        list.forEach(System.out::println);   //一种简写形式function包下面的被@FunctionalInterface注解声明的Consumer<T>接口





        //【3】条件过滤
        List<Integer> number = new ArrayList<Integer>();
        for (int i = 0; i < 100; i++) { //0-99
            number.add(i);
        }

        //过滤小于90的数字
        List<Integer> numLessNinety = number.stream().filter(a->a>=90).collect(Collectors.toList());
        numLessNinety.forEach(System.out::print);
        System.out.println();

//------------------------------------------------------------------------

        //【4】批量增减操作
        List<Double> salary = Arrays.asList(8000.0,7600.0,8908.0);  //这样的list不可以用来做add操作会报错

        for (Double aDouble : salary) {
            System.out.println(aDouble*1.3);
        }

        salary.stream().map(a->a*1.3).forEach(System.out::println);


        //【4】求最大值、最小值、平均值

        List<Integer> numbers= new ArrayList<Integer>();
        for(int i = 0 ;i < 100; i++)
            numbers.add(i);
        IntSummaryStatistics iss = numbers.stream().mapToInt((x)->x).summaryStatistics();
        System.out.println("最大值:"+iss.getMax());
        System.out.println("最小值:"+iss.getMin());
        System.out.println("求和:"+iss.getSum());
        System.out.println("统计个数:"+iss.getCount());


    }
}
