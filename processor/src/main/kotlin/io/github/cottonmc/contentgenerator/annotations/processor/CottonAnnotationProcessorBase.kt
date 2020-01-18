package io.github.cottonmc.contentgenerator.annotations.processor

import io.github.cottonmc.modhelper.api.annotations.Modid
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.lang.model.util.ElementFilter
import javax.lang.model.util.Elements


abstract class CottonAnnotationProcessorBase : AbstractProcessor() {


    private lateinit var processingEnvironment: ProcessingEnvironment
    private lateinit var elementUtils: Elements


    //only support release 8, we do not want to mess with mixins.
    override fun getSupportedSourceVersion() = SourceVersion.RELEASE_8

    private var processed = false
    private lateinit var modid: String
    private lateinit var packageName: String

    fun getModid(): String {
        return modid
    }

    fun getPackage(): String {
        return packageName
    }

    override fun init(p0: ProcessingEnvironment?) {
        super.init(p0)
        this.processingEnvironment = processingEnvironment
        this.elementUtils = processingEnvironment.elementUtils

    }

    override fun process(p0: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment): Boolean {

        if (!processed) {
            //get basic information about the environment.
            val modidElements =
                roundEnv.getElementsAnnotatedWith(io.github.cottonmc.modhelper.api.annotations.Modid::class.java)

            if (modidElements.size > 1) {
                throw ProcessingException("Multiple @Modid annotations found! You can only have one!")
            } else if (modidElements.isEmpty()) {
                throw ProcessingException("No @Modid annotations found! You must have one to use cotton annotations!")
            }

            val fields = ElementFilter.fieldsIn(modidElements)
            modid = fields.first().constantValue.toString()

            packageName = elementUtils.getPackageOf(modidElements.first()).qualifiedName.toString()
            fields.first().enclosingElement.asType().kind
            doProcess(p0, roundEnv)
        }

        processed = true

        //return false, so we doN't prevent other processors from accessing our stuff.
        return false
    }

    abstract fun doProcess(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment)


    protected fun getBinaryName(element: TypeElement): String =
        processingEnv.elementUtils.getBinaryName(element).toString()

    companion object {
        const val MOD_INITIALIZER = "net.fabricmc.api.ModInitializer"
        const val CLIENT_MOD_INITIALIZER = "net.fabricmc.api.ClientModInitializer"
        const val SERVER_MOD_INITIALIZER = "net.fabricmc.api.DedicatedServerModInitializer"
        const val MIXIN = "org.spongepowered.asm.mixin.Mixin"
        const val ENVIRONMENT = "net.fabricmc.api.Environment"
    }

    /**
     * The annotations that the processor actually owns, and not shared.
     * */
    protected abstract fun getOwnedAnnotations(): MutableSet<String>

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return (getOwnedAnnotations() + mutableSetOf(io.github.cottonmc.modhelper.api.annotations.Modid::class.java.canonicalName)).toMutableSet()
    }
}