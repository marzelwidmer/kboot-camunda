package ch.keepcalm.demo

import org.camunda.bpm.client.spring.annotation.EnableExternalTaskClient
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.beans
import org.springframework.stereotype.Component

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
    }
}
