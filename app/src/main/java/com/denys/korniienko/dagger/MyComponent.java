package com.denys.korniienko.dagger;

import com.denys.korniienko.fragments.DetailFragment;
import com.denys.korniienko.fragments.MainFragmentViewModel;
import dagger.Component;

@Component(modules = {NetworkModule.class, AppModule.class, DatabaseModule.class})
public interface MyComponent {
    void inject(MainFragmentViewModel mainFragmentViewModel);
    void inject(DetailFragment detailFragment);
}
