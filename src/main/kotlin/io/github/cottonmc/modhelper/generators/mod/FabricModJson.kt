package io.github.cottonmc.modhelper.generators.mod

class FabricModJson {
    var schemaVersion:String ="1"
    var name:String=""
    var version:String = ""
    var icon:String=""
    var description:String=""
    var license:String=""
    var contact:FabricModContact= FabricModContact()
    var environment:String=""
    var entryPoints:FabricModEntryPoints = FabricModEntryPoints()
    var mixins:Array<String> = emptyArray()
    var depends:Map<String,String> = emptyMap()
}


class FabricModContact {
    var sources:String=""
}

class FabricModEntryPoint{
    var adapter:String=""
    var value:String="";
}
class FabricModEntryPoints{
    var main:Array<FabricModEntryPoint> =emptyArray()
    var client:Array<FabricModEntryPoint> =emptyArray()
}