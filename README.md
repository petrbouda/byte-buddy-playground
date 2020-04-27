# byte-buddy-playground

- https://bytebuddy.net/#/develop
- https://javadoc.io/doc/net.bytebuddy/byte-buddy/latest/index.html
- https://github.com/raphw/byte-buddy
- https://www.baeldung.com/byte-buddy
- https://www.infoq.com/articles/Easily-Create-Java-Agents-with-ByteBuddy/
- https://stackoverflow.com/questions/60317919/byte-buddy-method-interception-invocationhandler-vs-methoddelegation-to-general

##### Videos

- https://www.youtube.com/watch?v=OF3YFGZcQkg
- https://www.youtube.com/watch?v=hOUb5RP7xRc
- https://www.youtube.com/watch?v=Gjtrl66J26g&t=1653s

##### Problem with CGLIB and JPMS

Old proxying (CGLIB) that implements interface Does not work well with JPMS
- INTERFACE can be from a different module and we need to add REQUIRES explicitly otherwise it does not work.
- Boundaries between JAR files

#### ByteBuddy Maven Plugin

```xml
<plugin>
    <groupId>net.bytebuddy</groupId>
    <artifactId>byte-buddy-maven-plugin</artifactId>
    <executions>
        <execution>
            <goals>
                <goal>transform</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <transformations>
            <transformation>
                <plugin>pbouda.bytebuddy.MyPlugin</plugin>
            </transformation>
        </transformations>
    </configuration>
</plugin>
```