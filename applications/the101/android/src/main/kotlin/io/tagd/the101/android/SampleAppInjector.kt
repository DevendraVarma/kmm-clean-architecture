package io.tagd.the101.android

import io.tagd.android.app.TagdApplicationInjector
import io.tagd.arch.domain.usecase.Callback
import io.tagd.arch.domain.usecase.Command
import io.tagd.arch.scopable.library.Library
import io.tagd.di.Global
import io.tagd.di.key
import io.tagd.di.layer

class SampleAppInjector(
    application: SampleApplication
) : TagdApplicationInjector<SampleApplication>(application) {

    override fun inject(callback: Callback<Unit>) {
        super.inject(callback)
        with(Global) {
            layer<Library> {
                bind(key(), initSampleLibrary())
            }
        }
    }

    private fun initSampleLibrary(): SampleLibrary {
        return SampleLibrary.Builder()
            .name("sample")
            .inject {
                println("inside library specific injection")
                layer<Command<*, *>> {
                    bind(key(), LibraryUsecase())
                }
            }.injectBidirectionalDependents {
                println("inside bidirectional dependents")
            }
            .build()
    }
}