package org.ch2;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;


/**
 * VM 매개 변수: (JDK 7이하) -XX:PermSize=10M -XX:MaxPermSize=10M
 * VM 매개 변수: (JDK 8이상) -XX:MetaspaceSize=10M -XX:MaxMetaspaceSize=10M
 */
public class JavaMethodAreaOOM {
    public static void main(String[] args) {
        while (true) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(OOMObject.class);
            enhancer.setUseCache(false);
            enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) ->
                    methodProxy.invokeSuper(o, objects)
            );
            enhancer.create();
        }
    }

    static class OOMObject {}
}
