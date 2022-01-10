# Blog项目前后端分离错误文档

## 1.项目

![image-20210927230524932](C:\Users\86130\AppData\Roaming\Typora\typora-user-images\image-20210927230524932.png)

## 2.加密盐

### 为什么密码需要进行哈希

```
hash("hello") = 2cf24dba5fb0a30e26e83b2ac5b9e29e1b161e5c1fa7425e73
043362938b9824
hash("hbllo") = 58756879c05c68dfac9866712fad6a93f8146f337a69afe7dd238f3364946366
hash("waltz") = c0e81794384491161f1777c232bc6bd9ec38f616560b120fda8e90f383853542` 
```



哈希算法是一个单向函数。它可以将任何大小的数据转化为定长的“指纹”，并且无法被反向计算。另外，即使数据源只改动了一丁点，哈希的结果也会完全不同（参考上面的例子）。![image-20211010142108104](C:\Users\86130\AppData\Roaming\Typora\typora-user-images\image-20211010142108104.png)

对于每个用户的每个密码，盐值都应该是独一无二的。每当有新用户注册或者修改密码，都应该使用新的盐值进行加密。并且这个盐值也应该足够长，使得有足够多的盐值以供加密。一个好的标准的是：盐值至少和哈希函数的输出一样长；盐值应该被储存和密码哈希一起储存在账户数据表中。

### **存储密码的步骤**

1. 使用CSPRNG生成一个长度足够的盐值
2. 将盐值混入密码，并使用**标准的**加密哈希函数进行加密，如SHA256
3. 把哈希值和盐值一起存入数据库中对应此用户的那条记录



### **校验密码的步骤**

1. 从数据库取出用户的密码哈希值和对应盐值

2. 将盐值混入用户输入的密码，并且使用同样的哈希函数进行加密

3. 比较上一步的结果和数据库储存的哈希值是否相同，如果相同那么密码正确，反之密码错误

   

### 让密码更难破解：慢哈希函数



### **无法破解的哈希加密：密钥哈希和密码哈希设备**







## 3.BaseMapper接口继承



#### 置顶排序





## 4.vo对象

实现与前端resquest、response对象的值相同一致

## 5.最热标签





## 6.CollectionUtils

CollectionUtils工具类：

1. 判断集合是否为空

   　CollectionUtils.isEmpty(null): true

2. 判断集合长度

   CollectionUtils.sizeIsEmpty({a,b}): falseA

3. 并集

   ```java
     String[] arrayA = new String[] { "A", "B", "C", "D", "E", "F" };  
       String[] arrayB = new String[] { "B", "D", "F", "G", "H", "K" };
       List<String> listA = Arrays.asList(arrayA);
       List<String> listB = Arrays.asList(arrayB);
       //2个数组取并集 
       System.out.println(ArrayUtils.toString(CollectionUtils.union(listA, listB)));
       //[A, B, C, D, E, F, G, H, K]
   
   ```

4. 交集

   ```java
   @Test
   public void testIntersection(){
       String[] arrayA = new String[] { "A", "B", "C", "D", "E", "F" };  
       String[] arrayB = new String[] { "B", "D", "F", "G", "H", "K" };
       List<String> listA = Arrays.asList(arrayA);
       List<String> listB = Arrays.asList(arrayB);
       //2个数组取交集 
       System.out.println(ArrayUtils.toString(CollectionUtils.intersection(listA, listB)));
       //[B, D, F]
    
   }
   ```

5. 交集的补集

   ```java
   @Test
   public void testDisjunction(){
       String[] arrayA = new String[] { "A", "B", "C", "D", "E", "F" };  
       String[] arrayB = new String[] { "B", "D", "F", "G", "H", "K" };
       List<String> listA = Arrays.asList(arrayA);
       List<String> listB = Arrays.asList(arrayB);
       //2个数组取交集 的补集
       System.out.println(ArrayUtils.toString(CollectionUtils.disjunction(listA, listB)));
       //[A, C, E, G, H, K]
   }
   ```

6. 差集

   ```java
   @Test
   public void testSubtract(){
       String[] arrayA = new String[] { "A", "B", "C", "D", "E", "F" };  
       String[] arrayB = new String[] { "B", "D", "F", "G", "H", "K" };
       List<String> listA = Arrays.asList(arrayA);
       List<String> listB = Arrays.asList(arrayB);
       //arrayA扣除arrayB
       System.out.println(ArrayUtils.toString(CollectionUtils.subtract(listA, listB)));
       //[A, C, E]
    
   }
   ```

7. 集合是否相等

需要导入配置依赖

```pom
<!-- https://mvnrepository.com/artifact/commons-collections/commons-collections -->
<dependency>
    <groupId>commons-collections</groupId>
    <artifactId>commons-collections</artifactId>
    <version>3.2.2</version>
</dependency>

```



## 7.emus



## 8.count(*![image-20210930130725267](C:\Users\86130\AppData\Roaming\Typora\typora-user-images\image-20210930130725267.png))



## 9.jwt



## 10. RequestBody 和RequestParam





## 11. redis





## 12LambdaQueryWrapper



## 13 IOC和AOP
