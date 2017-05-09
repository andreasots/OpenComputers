package li.cil.oc.common.tileentity.traits.power

import net.minecraftforge.energy.{CapabilityEnergy, IEnergyStorage}
import li.cil.oc.integration.util.Power
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability

class ForgeEnergyStorage(var inner: Common, var facing: EnumFacing) extends IEnergyStorage {
  override def receiveEnergy(maxReceive: Int, simulate: Boolean): Int = Power.toRF(inner.tryChangeBuffer(facing, Power.fromRF(maxReceive), !simulate))

  override def extractEnergy(maxExtract: Int, simulate: Boolean): Int = 0

  override def getEnergyStored(): Int = Power.toRF(inner.globalBuffer(facing))

  override def getMaxEnergyStored(): Int = Power.toRF(inner.globalBufferSize(facing))

  override def canExtract(): Boolean = false

  override def canReceive(): Boolean = true
}

trait Forge extends Common {
  override def hasCapability(capability: Capability[_], facing: EnumFacing): Boolean = {
    capability == CapabilityEnergy.ENERGY && canConnectPower(facing) || super.hasCapability(capability, facing)
  }

  override def getCapability[T](capability: Capability[T], facing: EnumFacing): T = {
    if (capability == CapabilityEnergy.ENERGY) {
      capability.cast(new ForgeEnergyStorage(this, facing).asInstanceOf[T])
    } else {
      super.getCapability(capability, facing)
    }
  }
}
