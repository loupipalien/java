package com.ltchen.java.jvm.chapter10;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.*;
import javax.lang.model.util.ElementScanner6;
import javax.tools.Diagnostic;

/**
 * @author : ltchen
 * @date : 2018/4/22
 * @desc : 程序名称规范的编译器插件: 如果程序命名不合规范, 将会输出一个编译器的 WARNING 信息
 */
public class NameChecker {

    private final Messager messager;

    NameCheckScanner nameCheckScanner = new NameCheckScanner();

    public NameChecker(ProcessingEnvironment processingEnv) {
        this.messager = processingEnv.getMessager();
    }

    public void checkNames(Element element) {
        nameCheckScanner.scan(element);
    }

    private class NameCheckScanner extends ElementScanner6<Void,Void> {

        /**
         * 此方法用于检查 Java 类
         * @param e
         * @param p
         * @return
         */
        @Override
        public Void visitType(TypeElement e, Void p) {
            super.visitType(e, p);
            return null;
        }

        /**
         * 检查方法命名是否合法
         * @param e
         * @param p
         * @return
         */
        @Override
        public Void visitExecutable(ExecutableElement e, Void p) {
            if (e.getKind() == ElementKind.METHOD) {
                Name name = e.getSimpleName();
                if (name.contentEquals(e.getEnclosingElement().getSimpleName())) {
                    messager.printMessage(Diagnostic.Kind.WARNING, "普通方法 " + name + " 不应当与类名重复, 避免与构造器函数混淆!", e);
                    // TODO
                }

            }
            super.visitExecutable(e, p);
            return null;
        }

        /**
         * 检查传入的 Element 是否符合驼式命名法, 如果不符合则输出警告信息
         * @param e
         * @param initialCaps
         */
        private void checkCamelCase(Element e, boolean initialCaps) {
            String name = e.getSimpleName().toString();
            boolean previousUpper = false;
            boolean conventional = true;
            int firstCodePoint = name.codePointAt(0);
            if (Character.isUpperCase(firstCodePoint)) {
                previousUpper = true;
                if (!initialCaps) {
                    messager.printMessage(Diagnostic.Kind.WARNING, "名称 " + name + " 应当以小写字母开头!", e);
                    return;
                }
            } else if (Character.isLowerCase(firstCodePoint)) {
                if (initialCaps) {
                    messager.printMessage(Diagnostic.Kind.WARNING, "名称 " + name + " 应当以大写字母开头!", e);
                    return;
                }
            } else {
                conventional = false;
            }

            if (conventional) {
                int cp = firstCodePoint;

            }
        }

    }

}
