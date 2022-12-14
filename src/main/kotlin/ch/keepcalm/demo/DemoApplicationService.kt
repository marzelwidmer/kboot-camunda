package ch.keepcalm.demo

import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.RepositoryService
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.hateoas.config.EnableHypermediaSupport
import org.springframework.hateoas.support.WebStack
import org.springframework.stereotype.Component
import org.springframework.web.server.adapter.ForwardedHeaderTransformer

@SpringBootApplication
@EnableHypermediaSupport(stacks = [WebStack.WEBFLUX], type = [EnableHypermediaSupport.HypermediaType.HAL])
@EnableProcessApplication
class DemoApplicationService {

    @Bean
    fun forwardedHeaderTransformer(): ForwardedHeaderTransformer? {
        return ForwardedHeaderTransformer()
    }
}




fun main(args: Array<String>) {
    runApplication<DemoApplicationService>(*args).addApplicationListener {
//        addInitializers(
//            beans {
//                bean {
//                    ApplicationRunner { println("ApplicationRunner ----------------->") }
//                }
//            }
//        )
    }
}



@Component
class MyFooBean() : JavaDelegate {
    override fun execute(p0: DelegateExecution?) {
        println("-------------> Hello from MyFooBean")
    }
}

@Component
class MyBarBean() : JavaDelegate {
    override fun execute(p0: DelegateExecution?) {
        println("-------------> Hello from MyBarBean")
    }
}

@Component
class Starter (private var runtimeService: RuntimeService, private val repositoryService: RepositoryService) : InitializingBean {


    @Throws(Exception::class)
    override fun afterPropertiesSet() {
        repositoryService.createDeployment()
            .addClasspathResource("franchise_change.bpmn")
            .deploy();



        println("----------------------------------///////////////>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> START ")
        runtimeService.startProcessInstanceById("MyFooBarProcess")
//        startProcessInstanceByKey("MyFooBarProcess")
    }
}
