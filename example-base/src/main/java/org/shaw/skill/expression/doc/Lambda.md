## 函数接口
> - 只拥有一个方法的接口称为 **函数式接口**

## 目标类型的上下文
> ### 范围:
> - 变量声明
> - 赋值
> - 返回语句
> - 数组初始化器
> - 方法和构造方法的参数
> - lambda 表达式函数体
> - 条件表达式（? :）
> - 转型（Cast）表达式  

## Lambda表达式
> lambda 表达式的语法由参数列表、箭头符号 -> 和函数体组成。函数体既可以是一个表达式，也可以是一个语句块：  

> - 表达式：表达式会被执行然后返回执行结果。
>  - 表达式只能出现在目标类型为函数式接口的上下文中。
> - 语句块：语句块中的语句会被依次执行，就像方法中的语句一样
>  - return 语句会把控制权交给匿名方法的调用者
>  - break 和 continue 只能在循环中使用
>  - 如果函数体有返回值，那么函数体内部的每一条路径都必须返回值
# 
> 编译器会检查 lambda 表达式的类型和目标类型的方法签名（method signature）是否一致。当且仅当下面所有条件均满足时，lambda 表达式才可以被赋给目标类型 T： 
> - T 是一个函数式接口
> - lambda 表达式的参数和 T 的方法参数在数量和类型上一一对应  
> - lambda 表达式的返回值和 T 的方法返回值相兼容（Compatible）  
> - lambda 表达式内所抛出的异常和 T 的方法 throws 类型相兼容  
# 
> ### 当无法解析目标类型的情况:
>  - 使用显式 lambda 表达式（为参数 p 提供显式类型）以提供额外的类型信息
>  - 把 lambda 表达式转型为 Function<Person, String>
>  - 为泛型参数 R 提供一个实际类型。（.<String>map(p -> p.getName())）  

## 词法作用域

> lambda 表达式不会从超类（supertype）中继承变量名，也不会引入一个新的作用域。lambda 表达式基于词法作用域，也就是说lambda表达式函数体里面的变量和它外部环境的变量具有相同的语义（也包括 lambda 表达式的形式参数）。此外，"this"关键字及其引用在 lambda 表达式内部和外部也拥有相同的语义
> ```
> public class Hello {
>   Runnable r1 = () -> { System.out.println(this); }
>   Runnable r2 = () -> { System.out.println(toString()); }
>   public String toString() {  return "Hello, world"; }
>     public static void main(String... args) {
>     new Hello().r1.run();
>     new Hello().r2.run();
>   }
> }
```  

## 变量捕获
> 一个局部变量在初始化后从未被修改过，那么它就符合有效只读的要求，换句话说，加上 final 后也不会导致编译错误的局部变量就是有效只读变量
> ```
Callable<String> helloCallable(String name) {
  String hello = "Hello";
  return () -> (hello + ", " + name);
}
> ```
### 
>对 **this** 的引用，以及通过 **this** 对未限定字段的引用和未限定方法的调用在本质上都属于使用 final 局部变量。包含此类引用的 lambda 表达式相当于捕获了 this 实例。在其它情况下，lambda 对象不会保留任何对 this 的引用。

> 这个特性对内存管理是一件好事：内部类实例会一直保留一个对其外部类实例的强引用，而那些没有捕获外部类成员的 lambda 表达式则不会保留对外部类实例的引用。要知道内部类的这个特性往往会造成内存泄露。
### 
> lambda 表达式对 **值** 封闭，对 **变量** 开放
> ```
int sum = 0;
list.forEach(e -> { sum += e.size(); }); // Illegal, close over values
List<Integer> aList = new List<>();
list.forEach(e -> { aList.add(e); }); // Legal, open over variables
> ```

## 规约（reduction）
> java.util.stream 包提供了各种通用的和专用的规约操作（例如 sum、min 和 max）
> ```
int sum =
    list.stream()
        .mapToInt(e -> e.size())
        .reduce(0 , (x, y) -> x + y); // 规约需要一个初始值（以防输入为空）和一个操作符（在这里是加号）
> ```

## 方法引用（Method references）
> 方法引用和 lambda 表达式拥有相同的特性（例如，都需要一个目标类型，并需要被转化为函数式接口的实例），不过并不需要为方法引用提供方法体，可以通过方法名称引用已有方法。
>```
Consumer<Integer> b1 = System::exit;    // void exit(int status)
Consumer<String[]> b2 = Arrays:sort;    // void sort(Object[] a)
Consumer<String> b3 = MyProgram::main;  // void main(String... args)
Runnable r = Myprogram::mapToInt        // void main(String... args)
>```

## 方法引用的种类（Kinds of method references
> - 静态方法引用：ClassName::methodName
> - 实例上的实例方法引用：instanceReference::methodName
> - 超类上的实例方法引用：super::methodName
> - 类型上的实例方法引用：ClassName::methodName
> - 构造方法引用：Class::new
> - 数组构造方法引用：TypeName[]::new
### 
> 如果类型的实例方法是泛型的，那么我们就需要在 :: 分隔符前提供类型参数，或者（多数情况下）利用目标类型推导出其类型。

##  默认方法和静态接口方法（Default and static interface methods）
> 默认方法 利用面向对象的方式向接口增加新的行为。接口方法可以是 **抽象的** 或是 默认的。默认方法拥有其默认实现，实现接口的类型通过继承得到该默认实现（如果类型没有覆盖该默认实现）。此外，默认方法不是抽象方法，所以我们可以放心的向函数式接口里增加默认方法，而不用担心函数式接口的单抽象方法限制。
> <br>
> 当类型或者接口的超类拥有多个具有相同签名的方法时，我们就需要一套规则来解决这个冲突：

> - 类的方法（class method）声明优先于接口默认方法。无论该方法是具体的还是抽象的。
> - 被其它类型所覆盖的方法会被忽略。这条规则适用于超类型共享一个公共祖先的情况。
> - 当两个独立的默认方法相冲突或是默认方法和抽象方法相冲突时会产生编译错误。这时程序员需要显式覆盖超类方法。一般来说我们会定义一个默认方法，然后在其中显式选择超类方法：
> ```
interface Robot implements Artist, Gun {
  default void draw() { Artist.super.draw(); } // super 前面的类型必须是有定义或继承默认方法的类型。这种方法调用并不只限于消除命名冲突——我们也可以在其它场景中使用它。
}
> ```
### 
> 静态方法:允许在接口中定义 **静态** 方法。这使得我们可以从接口直接调用和它相关的辅助方法（Helper method），而不是从其它的类中调用（之前这样的类往往以对应接口的复数命名，例如 Collections）。