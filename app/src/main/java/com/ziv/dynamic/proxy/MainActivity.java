package com.ziv.dynamic.proxy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RealSubject realSubject = new RealSubject();
        ProxyHandler proxyHandler = new ProxyHandler(realSubject);
        Subject proxyInstance = (Subject) Proxy.newProxyInstance(RealSubject.class.getClassLoader(), RealSubject.class.getInterfaces(), proxyHandler);
        proxyInstance.request();
    }

    /**
     * 接口
     */
    interface Subject{
        void request();
    }

    /**
     * 委托类
     */
    class RealSubject implements Subject {
        @Override
        public void request() {
            Log.d("MainActivity","RealSubject.request() - Hello world!");
        }
    }

    /**
     * 代理类的调用处理器
     */
    class ProxyHandler implements InvocationHandler {
        private Subject subject;
        public ProxyHandler(Subject subject){
            this.subject = subject;
        }

        @Override
        public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
            //定义预处理的工作，当然你也可以根据 method 的不同进行不同的预处理工作
            Log.d("MainActivity","ProxyHandler.invoke() - === before ===");
            Object result = method.invoke(subject, objects);
            Log.d("MainActivity","ProxyHandler.invoke() - === after ===");
            return result;
        }
    }
}
