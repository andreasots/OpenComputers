package li.cil.oc.common.block

import li.cil.oc.Config
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.common.ForgeDirection

/** Used for sub blocks that need special rendering. */
class SpecialMulti(id: Int) extends Delegator(id) {
  override def getRenderType = Config.blockRenderId

  override def isBlockNormalCube(world: World, x: Int, y: Int, z: Int) =
    subBlock(world.getBlockMetadata(x, y, z)) match {
      case None => false
      case Some(subBlock) => subBlock.isBlockNormalCube(world, x, y, z)
    }

  override def isBlockSolid(world: IBlockAccess, x: Int, y: Int, z: Int, side: Int) =
    subBlock(world.getBlockMetadata(x, y, z)) match {
      case None => true
      case Some(subBlock) => subBlock.isBlockSolid(
        world, x, y, z, ForgeDirection.getOrientation(side))
    }

  override def isOpaqueCube = false

  override def renderAsNormalBlock = false

  override def shouldSideBeRendered(world: IBlockAccess, x: Int, y: Int, z: Int, side: Int) =
    subBlock(world.getBlockMetadata(x, y, z)) match {
      case None => super.shouldSideBeRendered(world, x, y, z, side)
      case Some(subBlock) => subBlock.shouldSideBeRendered(
        world, x, y, z, ForgeDirection.getOrientation(side))
    }
}