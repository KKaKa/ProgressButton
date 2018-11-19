# ProgressButton
Android Progress Button

## 效果

![image](https://github.com/KKaKa/ProgressButton/blob/master/GIF.gif)

## 描述 

这是一个可以简单切换状态的Button


## 使用

```
repositories {
    maven {
        url 'https://dl.bintray.com/zexinlai0307/maven'
    }
}
...
  
dependencies {
    compile 'compile 'com.kkaka.ui:library:1.0.0''
}
```

```
progressButton.setProgress(0);//普通状态
progressButton.setProgress(-1);//错误状态
progressButton.setProgress(100);//完成状态
progressButton.setProgress(50);//加载状态
```

## 其他属性

### 改变文字/文字颜色

```
pb_normalText
pb_completeText
pb_errorText
pb_progressText

pb_normalTextColor
pb_completelTextColor
pb_errorTextColor
```

### 改变背景颜色

```
pb_normalBackgroundColor
pb_errorBackgroundColor
pb_completeBackgroundColor
pb_progressColor
pb_progress_indicator_color
pb_progress_indicator_background_color
```

### 完成/错误状态图片

```
pb_iconError
pb_iconComplete
```



