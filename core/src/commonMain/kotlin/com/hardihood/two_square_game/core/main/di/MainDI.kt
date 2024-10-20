package com.hardihood.two_square_game.core.main.di

import com.hardihood.two_square_game.core.main.data.api.ktor_services.MainKtorServices
import com.hardihood.two_square_game.core.main.data.data_source.MainDataSource
import com.hardihood.two_square_game.core.main.data.repository.CreateOrJoinRoomRepository
import com.hardihood.two_square_game.core.main.data.repository.DeleteRoomRepository
import com.hardihood.two_square_game.core.main.data.repository.GetDataRoomRepository
import com.hardihood.two_square_game.core.main.data.repository.LogoutRoomRepository
import com.hardihood.two_square_game.core.main.data.repository.MainRepositoryImp
import com.hardihood.two_square_game.core.main.data.repository.PlayGameRepository
import com.hardihood.two_square_game.core.main.domain.repository.MainRepository
import com.hardihood.two_square_game.core.main.domain.use_case.CreateOrJoinRoomUseCase
import com.hardihood.two_square_game.core.main.domain.use_case.DeleteRoomUseCase
import com.hardihood.two_square_game.core.main.domain.use_case.GetDataRoomUseCase
import com.hardihood.two_square_game.core.main.domain.use_case.LogoutRoomUseCase
import com.hardihood.two_square_game.core.main.domain.use_case.PlayGameUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

private val dataModule = module {

    factory { MainKtorServices() }
    factory { MainDataSource(get(), get()) }
}


private val domainModule = module {
    factory { CreateOrJoinRoomRepository(get()) }

    factory { LogoutRoomRepository(get()) }

    factory { DeleteRoomRepository(get()) }

    factory { PlayGameRepository(get()) }

    factory { GetDataRoomRepository(get()) }


    single<MainRepository> { MainRepositoryImp(get(), get(), get(), get(), get()) }

    factory { CreateOrJoinRoomUseCase() }

    factory { PlayGameUseCase() }

    factory { GetDataRoomUseCase() }

    factory { LogoutRoomUseCase() }

    factory { DeleteRoomUseCase() }


}
private val sharedModules = listOf<Module>(dataModule, domainModule)

internal fun mainSharedModules() = sharedModules