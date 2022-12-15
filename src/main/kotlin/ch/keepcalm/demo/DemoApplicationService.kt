package ch.keepcalm.demo

import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication
import org.springframework.beans.factory.InitializingBean
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.support.beans
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import javax.annotation.PostConstruct


@SpringBootApplication
@EnableProcessApplication
class DemoApplicationService


fun main(args: Array<String>) {
    runApplication<DemoApplicationService>(*args) {
        addInitializers(
            beans {
                bean {
                    ApplicationRunner {}
                }
            }
        )
    }
}

@Component
class MyFooBean() : JavaDelegate {
    override fun execute(delegate: DelegateExecution?) {
        println("-------------> Hello from MyFooBean")
    }
}

@Component
class MyBarBean() : JavaDelegate {
    override fun execute(delegate: DelegateExecution?) {
        println("-------------> Hello from MyBarBean")
//        Thread.sleep(60000)
        println("-------------> wake up du sack")
    }
}

@RestController
class FooBar(private val runtimeService: RuntimeService){

    @GetMapping(value = ["/start"])
    fun start(): String {
        runtimeService.startProcessInstanceByKey("myFooBarProcess")
        return "started.."
    }

}

//
//@Component
//class StartProcessInstanceBean(private val runtimeService: RuntimeService) {
//    @PostConstruct
//    fun postConstruct() {
//        runtimeService.startProcessInstanceByKey("myFooBarProcess")
//    }
//}


















