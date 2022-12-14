package ch.keepcalm.demo

import mu.KLogging
import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.support.beans
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.io.Serializable
import java.lang.RuntimeException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@SpringBootApplication
@EnableProcessApplication
@EnableScheduling
class DemoApplicationService


fun main(args: Array<String>) {
    runApplication<DemoApplicationService>(*args) {
        addInitializers(
            beans {
                bean {
                    ApplicationRunner {
                        println("ApplicationRunner :////////------------->>>")
//                        val runtimeService = ref<RuntimeService>()
//                        runtimeService.startProcessInstanceByKey("myFooBarProcess")
                    }
                }
            }
        )
    }
}


data class Person(val name: String?, val vorname: String?) : Serializable {

    constructor() : this("", "")

}

@Component
class MyFooBean() : JavaDelegate {

    override fun execute(delegate: DelegateExecution?) {
        println("-------------> Hello from MyFooBean")
        delegate?.setVariable("key","Hello")
        delegate?.setVariable("myKey", Person(name = "Meister", vorname = "Marcel"))
    }
}

@Component
class MyBarBean() : JavaDelegate {
    override fun execute(delegate: DelegateExecution?) {
        println("-------------> Hello from MyBarBean")
        val person: Person = delegate?.getVariable("myKey") as Person
        println(person)

        if(person.name != "Widmer") {
            throw RuntimeException("Person exception ${person.name}")
        }


//        Thread.sleep(10_000_000)
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



/**
 * Mark this class an injectable component so that the Spring environment will create
 * an instance of this class when it starts up.
 */
//@Component
//class ScheduleTasks(private val runtimeService: RuntimeService)  {
//
//    companion object : KLogging()
//
//    /**
//     * run every 5 seconds
//     */
//    @Scheduled(fixedRate = 5000)
//    fun reportTime(){
//        logger.info("The time is now ${DateTimeFormatter.ISO_LOCAL_TIME.format(LocalDateTime.now())}")
//        runtimeService.startProcessInstanceByKey("myFooBarProcess")
//    }
//}











