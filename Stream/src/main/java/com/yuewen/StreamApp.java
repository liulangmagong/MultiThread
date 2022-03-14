package com.yuewen;

import com.yuewen.dto.Student;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author wangshiyang
 * @since 2022/3/14
 **/
public class StreamApp {
    public static void main(String[] args) {
        // 查询年龄小于 20 岁的学生，并且根据年龄进行排序 得到学生姓名 生成新集合
        ArrayList<Student> studentList = new ArrayList<>();
        studentList.add(new Student(1, "张三", "M", 18));
        studentList.add(new Student(2, "张三1", "M", 39));
        studentList.add(new Student(3, "张三1", "F", 14));
        studentList.add(new Student(4, "张三1", "F", 12));
        studentList.add(new Student(1, "张三", "M", 18));
        studentList.add(new Student(2, "张三1", "M", 39));
        studentList.add(new Student(3, "张三1", "F", 14));
        studentList.add(new Student(4, "张三1", "F", 12));

        // 数据过滤，排序，取值，输出操作
        // select name from student where age<20 order by age;
        List<String> result = studentList.stream()
                .filter(s -> s.getAge() < 18)  // 这里就是相当于for循环依次获取数据进行处理
                .sorted(Comparator.comparing(Student::getAge))  // 根据年龄进行排序
                .map(Student::getName)  // 得到学生的姓名  映射到每一个元素，获取每一个元素的name
                .collect(Collectors.toList());  // 生成新的集合
        System.out.println(result);

        // 数据去重操作
        // 对下列数据 模2 后去重
        List<Integer> numList = Arrays.asList(1, 2, 3, 4, 1, 2, 3, 4);
        List<Integer> soloResult = numList.stream()
                .filter(s -> s % 2 == 0)  // 拿到所有 模2 为0的值
                .distinct()  // 一次性实现去重
                .collect(Collectors.toList());
        System.out.println(soloResult);

        List<Student> result1 = studentList
                .stream()  // 将所有的数据放入到流中
                .distinct()  // 对所有的数据一次性去重
                .collect(Collectors.toList());
        System.out.println(result1);

        // 基于 limit 实现数据切片截取操作
        List<Integer> result2 = numList.stream()
                .limit(5)  // 截取前 5 个数据
                .collect(Collectors.toList());
        System.out.println(result2);

        // 基于 skip 实现数据跳过
        List<Integer> result3 = numList.stream()
                .skip(2)  // 跳过开始的两个数  就是忽略两个数
                .limit(5)  // 从第三个数开始选择5个数  这里的limit和skip的顺序可以变化
                .collect(Collectors.toList());
        System.out.println(result3);

        // 基于 map 实现元素类型变换  如 str -> int
        // map 就是实现将操作映射到流中的每一个元素
        List<String> numStrList = Arrays.asList("1", "2", "3", "4");
        int sum = numStrList.stream().mapToInt(Integer::parseInt).sum();
        System.out.println(sum);

        // 基于 anyMatch 添加判断条件 至少匹配一个元素  短路求值
        if (studentList.stream().anyMatch(s -> s.getAge() < 20)){
            System.out.println("已有符合的数据");
        }

        // 基于 allMatch 添加判断条件 所有的元素都匹配才可以
        if (studentList.stream().allMatch(s -> s.getAge() < 20)){
            System.out.println("所有学生都符合");
        }else {
            System.out.println("至少一个学生符合");
        }

        // 基于 findAny 查找元素
        // Optional类型的好处就是，如果返回的值是空值 ，不会报空指针异常
        // 会将空值结果转换为 null，这样就可以通过 isPresent()进行判断是否有值
        // findFirst 的使用方式一样，就是会返回符合条件的流中的第一个数据
        Optional<Student> optional = studentList.stream()
                .filter(s -> s.getAge() < 20)
                .findFirst();
        if (optional.isPresent()){
            Student student = optional.get();
            System.out.println(student);
        }

        // 基于 reduce() 进行累计求和
        Optional<Integer> optional1 = numList.stream().reduce(Integer::sum);
        if (optional1.isPresent()){
            Integer integer = optional1.get();
            System.out.println(integer);
        }

        // 获取最大值 最小值
        // 方式一
        Optional<Integer> optional2 = numList.stream().reduce(Integer::max);
        if (optional2.isPresent()){
            Integer integer = optional2.get();
            System.out.println(integer);
        }

        Optional<Integer> optional3 = numList.stream().reduce(Integer::min);
        if (optional3.isPresent()){
            Integer integer = optional3.get();
            System.out.println(integer);
        }

        // 方式二
        Optional<Integer> max = numList.stream().max(Integer::compareTo);
        if (max.isPresent()){
            Integer integer = max.get();
            System.out.println(integer);
        }

        Optional<Integer> min = numList.stream().min(Integer::compareTo);
        if (min.isPresent()){
            Integer integer = min.get();
            System.out.println(integer);
        }

        // 基于值创建流
        // 创建流的过程中 Stream 的范型会根据传入数据的类型自动判断
        Stream<String> stringStream = Stream.of("1", "2", "3");
        stringStream.forEach(System.out::println);

        // 传入不同类型的数据  生成的范型就是Object
        Stream<Object> objectStream = Stream.of("1", 1, true);

        // 通过数组的形式创建流
        Integer[] numbers = {1, 2, 3, 4, 5, 6};
        Stream<Integer> stream = Arrays.stream(numbers);


    }

}
