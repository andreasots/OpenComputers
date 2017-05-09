package li.cil.oc.integration.forge

import li.cil.oc.integration.{ModProxy, Mods}

object ModForge extends ModProxy {
  override def getMod = Mods.Forge

  override def initialize() = {
  }
}
