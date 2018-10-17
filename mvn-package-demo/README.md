# 说明

本工程是一个如何打包java工程为一个可执行文件的demo.

打包逻辑基本都是一样，参考pom文件的说明就行。

mvn的同一个phase中的goal的执行顺序按照pom文件定义的顺序执行。

maven-jar-plugin : 将本工程的代码打包成一个jar包。包含main的class。

maven-dependency-plugin : 复制项目依赖的jar包到某个目录。

maven-assembly-plugin:  复制各种资源文件整合到某个文件结构。

# maven命令说明

自定义打包：

mvn clean package -Pzip

所有资源打包为一个可运行的jar。结构固定

mvn clean package -Pjar