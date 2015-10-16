package eu.kanade.mangafeed;

import android.app.Application;
import android.content.Context;

import org.acra.annotation.ReportsCrashes;

import timber.log.Timber;

@ReportsCrashes(
        formUri = "http://couch.kanade.eu/acra-manga/_design/acra-storage/_update/report",
        reportType = org.acra.sender.HttpSender.Type.JSON,
        httpMethod = org.acra.sender.HttpSender.Method.PUT,
        formUriBasicAuthLogin="test",
        formUriBasicAuthPassword="test"
)
public class App extends Application {

    AppComponent mApplicationComponent;
    ComponentReflectionInjector<AppComponent> mComponentInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) Timber.plant(new Timber.DebugTree());

        mApplicationComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

        mComponentInjector =
                new ComponentReflectionInjector<>(AppComponent.class, mApplicationComponent);

        //ACRA.init(this);
    }

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }

    public AppComponent getComponent() {
        return mApplicationComponent;
    }

    public ComponentReflectionInjector<AppComponent> getComponentReflection() {
        return mComponentInjector;
    }

    public static ComponentReflectionInjector<AppComponent> getComponentReflection(Context context) {
        return get(context).getComponentReflection();
    }

    public static AppComponent getComponent(Context context) {
        return get(context).getComponent();
    }

    // Needed to replace the component with a test specific one
    public void setComponent(AppComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
