# MSLibrary

### Site

> - http://msleague.kr

### Authors

> - Akarang
> - BGMSound
> - CChuYong
> - HuNi_C
> - Soa_

### Timer
> Kotlin
```kotlin
class TestPlugin: JavaPlugin(), TimeListener {

  override fun onEnable() {
    timerManager.registerTimeListener(this, this)
  }
  
  @TimeEventHandler(hour = 17)
  fun test() {
    TODO()
  }

}
```
> Java
```java
public class TestPlugin extends JavaPlugin implements TimeListener {
  @Override
  public void onEnable() {
      JavaPluginExtensionKt.getTimerManager(this).registerTimeListener(this, this);
  }
  
  @TimeEventHandler(hour = 15, min = 30)
  public void test() {
    //TODO
  }
}
```

### License

> - MIT License
> - <a href="https://github.com/TravelRPG/MSLibrary/blob/main/LICENSE">more info</a>

<img src="https://resources.jetbrains.com/storage/products/company/brand/logos/jb_beam.png" alt="JetBrains Logo (Main) logo." width=300 height=300>
Supported by JetBrains Open Source Project
