package com.jaquadro.minecraft.gardentrees.world.gen;

import net.minecraft.block.Block;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public abstract class WorldGenStandardOrnTree extends WorldGenOrnamentalTree {

    protected List layers = new ArrayList();

    public WorldGenStandardOrnTree(boolean blockNotify, Block wood, int metaWood, Block leaves, int metaLeaves) {
        super(blockNotify, wood, metaWood, leaves, metaLeaves);
    }

    protected boolean canGenerateCanopy(World world, int x, int y, int z, int trunkHeight) {
        for (int i = 0; i < this.layers.size(); ++i) {
            if (!this.canGeneratePattern(world, x, y + trunkHeight + i, z, (String) this.layers.get(i))) {
                return false;
            }
        }

        return true;
    }

    protected void generateCanopy(World world, int x, int y, int z, int trunkHeight) {
        for (int i = 0; i < this.layers.size(); ++i) {
            this.generatePattern(world, x, y + trunkHeight + i, z, (String) this.layers.get(i));
        }

    }

    public static class SmallCanopyTree extends WorldGenStandardOrnTree {

        public static final OrnamentalTreeFactory FACTORY = new OrnamentalTreeFactory() {

            public WorldGenOrnamentalTree create(Block woodBlock, int woodMeta, Block leafBlock, int leafMeta) {
                return new WorldGenStandardOrnTree.SmallCanopyTree(false, woodBlock, woodMeta, leafBlock, leafMeta);
            }
        };

        public SmallCanopyTree(boolean blockNotify, Block wood, int metaWood, Block leaves, int metaLeaves) {
            super(blockNotify, wood, metaWood, leaves, metaLeaves);
        }

        protected void prepare(World world, Random rand, int x, int y, int z, int trunkHeight) {
            this.layers = new ArrayList();
            this.layers.add(transform(" 0 1X2 3 ", WorldGenOrnamentalTree.LayerType.TRUNK, rand.nextInt()));
            this.layers.add(transform(" XXX XXXXXXXTXXXXXXX XXX ", WorldGenOrnamentalTree.LayerType.CORE));
            this.layers.add(transform("XXXXTXXXX", WorldGenOrnamentalTree.LayerType.LEAF));
        }
    }

    public static class SmallCyprusTree extends WorldGenStandardOrnTree {

        public static final OrnamentalTreeFactory FACTORY = new OrnamentalTreeFactory() {

            public WorldGenOrnamentalTree create(Block woodBlock, int woodMeta, Block leafBlock, int leafMeta) {
                return new WorldGenStandardOrnTree.SmallCyprusTree(false, woodBlock, woodMeta, leafBlock, leafMeta);
            }
        };

        public SmallCyprusTree(boolean blockNotify, Block wood, int metaWood, Block leaves, int metaLeaves) {
            super(blockNotify, wood, metaWood, leaves, metaLeaves);
            this.layers = Arrays.asList(
                transform(" X XTX X ", WorldGenOrnamentalTree.LayerType.CORE),
                transform("XXXXTXXXX", WorldGenOrnamentalTree.LayerType.CORE),
                transform(" X XTX X ", WorldGenOrnamentalTree.LayerType.LEAF),
                transform(" X XTX X ", WorldGenOrnamentalTree.LayerType.LEAF),
                transform("T", WorldGenOrnamentalTree.LayerType.LEAF));
        }
    }

    public static class LargeSpruceTree extends WorldGenStandardOrnTree {

        public static final OrnamentalTreeFactory FACTORY = new OrnamentalTreeFactory() {

            public WorldGenOrnamentalTree create(Block woodBlock, int woodMeta, Block leafBlock, int leafMeta) {
                return new WorldGenStandardOrnTree.LargeSpruceTree(false, woodBlock, woodMeta, leafBlock, leafMeta);
            }
        };

        public LargeSpruceTree(boolean blockNotify, Block wood, int metaWood, Block leaves, int metaLeaves) {
            super(blockNotify, wood, metaWood, leaves, metaLeaves);
        }

        protected void prepare(World world, Random rand, int x, int y, int z, int trunkHeight) {
            this.layers = new ArrayList();
            this.layers.add(transform("T", WorldGenOrnamentalTree.LayerType.CORE));
            this.layers.add(transform(" X XTX X ", WorldGenOrnamentalTree.LayerType.CORE));
            this.layers.add(transform("XXXXTXXXX", WorldGenOrnamentalTree.LayerType.CORE));
            this.layers.add(transform("  X   XXX XXTXX XXX   X  ", WorldGenOrnamentalTree.LayerType.CORE));
            this.layers.add(transform("XXXXTXXXX", WorldGenOrnamentalTree.LayerType.CORE));
            this.layers.add(transform(" X XTX X ", WorldGenOrnamentalTree.LayerType.CORE));
            this.layers.add(transform(" X XTX X ", WorldGenOrnamentalTree.LayerType.LEAF));
            this.layers.add(transform("T", WorldGenOrnamentalTree.LayerType.LEAF));
        }
    }

    public static class TallSmallOakTree extends WorldGenStandardOrnTree {

        public static final OrnamentalTreeFactory FACTORY = new OrnamentalTreeFactory() {

            public WorldGenOrnamentalTree create(Block woodBlock, int woodMeta, Block leafBlock, int leafMeta) {
                return new WorldGenStandardOrnTree.TallSmallOakTree(false, woodBlock, woodMeta, leafBlock, leafMeta);
            }
        };

        public TallSmallOakTree(boolean blockNotify, Block wood, int metaWood, Block leaves, int metaLeaves) {
            super(blockNotify, wood, metaWood, leaves, metaLeaves);
            this.layers = Arrays.asList(
                transform("T", WorldGenOrnamentalTree.LayerType.TRUNK),
                transform(" X XTX X ", WorldGenOrnamentalTree.LayerType.CORE),
                transform("XXXXTXXXX", WorldGenOrnamentalTree.LayerType.CORE),
                transform("T", WorldGenOrnamentalTree.LayerType.LEAF));
        }
    }

    public static class SmallShrubTree extends WorldGenStandardOrnTree {

        public static final OrnamentalTreeFactory FACTORY = new OrnamentalTreeFactory() {

            public WorldGenOrnamentalTree create(Block woodBlock, int woodMeta, Block leafBlock, int leafMeta) {
                return new WorldGenStandardOrnTree.SmallShrubTree(false, woodBlock, woodMeta, leafBlock, leafMeta);
            }
        };

        public SmallShrubTree(boolean blockNotify, Block wood, int metaWood, Block leaves, int metaLeaves) {
            super(blockNotify, wood, metaWood, leaves, metaLeaves);
            this.layers = Arrays.asList(
                transform(" X XTX X ", WorldGenOrnamentalTree.LayerType.LEAF),
                transform("T", WorldGenOrnamentalTree.LayerType.LEAF));
        }
    }

    public static class SmallMahoganyTree extends WorldGenStandardOrnTree {

        public static final OrnamentalTreeFactory FACTORY = new OrnamentalTreeFactory() {

            public WorldGenOrnamentalTree create(Block woodBlock, int woodMeta, Block leafBlock, int leafMeta) {
                return new WorldGenStandardOrnTree.SmallMahoganyTree(false, woodBlock, woodMeta, leafBlock, leafMeta);
            }
        };

        public SmallMahoganyTree(boolean blockNotify, Block wood, int metaWood, Block leaves, int metaLeaves) {
            super(blockNotify, wood, metaWood, leaves, metaLeaves);
            this.layers = Arrays.asList(
                transform(" X XTX X ", WorldGenOrnamentalTree.LayerType.TRUNK),
                transform("XXXXTXXXX", WorldGenOrnamentalTree.LayerType.LEAF),
                transform(" X XTX X ", WorldGenOrnamentalTree.LayerType.LEAF));
        }
    }

    public static class SmallPineTree extends WorldGenStandardOrnTree {

        public static final OrnamentalTreeFactory FACTORY = new OrnamentalTreeFactory() {

            public WorldGenOrnamentalTree create(Block woodBlock, int woodMeta, Block leafBlock, int leafMeta) {
                return new WorldGenStandardOrnTree.SmallPineTree(false, woodBlock, woodMeta, leafBlock, leafMeta);
            }
        };

        public SmallPineTree(boolean blockNotify, Block wood, int metaWood, Block leaves, int metaLeaves) {
            super(blockNotify, wood, metaWood, leaves, metaLeaves);
        }

        protected void prepare(World world, Random rand, int x, int y, int z, int trunkHeight) {
            this.layers = new ArrayList();
            this.layers.add(transform("0X XTX X ", WorldGenOrnamentalTree.LayerType.CORE, rand.nextInt()));
            this.layers.add(transform("T", WorldGenOrnamentalTree.LayerType.CORE));
            this.layers.add(transform(" X XTX X ", WorldGenOrnamentalTree.LayerType.CORE));
            this.layers.add(transform("T", WorldGenOrnamentalTree.LayerType.LEAF));
        }
    }

    public static class SmallWillowTree extends WorldGenStandardOrnTree {

        public static final OrnamentalTreeFactory FACTORY = new OrnamentalTreeFactory() {

            public WorldGenOrnamentalTree create(Block woodBlock, int woodMeta, Block leafBlock, int leafMeta) {
                return new WorldGenStandardOrnTree.SmallWillowTree(false, woodBlock, woodMeta, leafBlock, leafMeta);
            }
        };

        public SmallWillowTree(boolean blockNotify, Block wood, int metaWood, Block leaves, int metaLeaves) {
            super(blockNotify, wood, metaWood, leaves, metaLeaves);
            this.layers = Arrays.asList(
                transform("X X T X X", WorldGenOrnamentalTree.LayerType.CORE),
                transform("XXXXTXXXX", WorldGenOrnamentalTree.LayerType.CORE),
                transform(" X XTX X ", WorldGenOrnamentalTree.LayerType.LEAF));
        }
    }

    public static class SmallPalmTree extends WorldGenStandardOrnTree {

        public static final OrnamentalTreeFactory FACTORY = new OrnamentalTreeFactory() {

            public WorldGenOrnamentalTree create(Block woodBlock, int woodMeta, Block leafBlock, int leafMeta) {
                return new WorldGenStandardOrnTree.SmallPalmTree(false, woodBlock, woodMeta, leafBlock, leafMeta);
            }
        };

        public SmallPalmTree(boolean blockNotify, Block wood, int metaWood, Block leaves, int metaLeaves) {
            super(blockNotify, wood, metaWood, leaves, metaLeaves);
            this.layers = Arrays.asList(
                transform("T", WorldGenOrnamentalTree.LayerType.CORE),
                transform("X X T X X", WorldGenOrnamentalTree.LayerType.CORE),
                transform(" X XTX X ", WorldGenOrnamentalTree.LayerType.LEAF));
        }
    }

    public static class LargeOakTree extends WorldGenStandardOrnTree {

        public static final OrnamentalTreeFactory FACTORY = new OrnamentalTreeFactory() {

            public WorldGenOrnamentalTree create(Block woodBlock, int woodMeta, Block leafBlock, int leafMeta) {
                return new WorldGenStandardOrnTree.LargeOakTree(false, woodBlock, woodMeta, leafBlock, leafMeta);
            }
        };

        public LargeOakTree(boolean blockNotify, Block wood, int metaWood, Block leaves, int metaLeaves) {
            super(blockNotify, wood, metaWood, leaves, metaLeaves);
            this.layers = Arrays.asList(
                transform(" X XTX X ", WorldGenOrnamentalTree.LayerType.CORE),
                transform("  X   XXX XXTXX XXX   X  ", WorldGenOrnamentalTree.LayerType.CORE),
                transform(" XXX XXTXXXTTTXXXTXX XXX ", WorldGenOrnamentalTree.LayerType.CORE),
                transform("  X   XXX XXTXX XXX   X  ", WorldGenOrnamentalTree.LayerType.CORE),
                transform(" X X XXXXX XTX XXXXX X X ", WorldGenOrnamentalTree.LayerType.CORE),
                transform("XXXXTXXXX", WorldGenOrnamentalTree.LayerType.LEAF));
        }
    }

    public static class SmallAcaciaTree extends WorldGenStandardOrnTree {

        public static final OrnamentalTreeFactory FACTORY = new OrnamentalTreeFactory() {

            public WorldGenOrnamentalTree create(Block woodBlock, int woodMeta, Block leafBlock, int leafMeta) {
                return new WorldGenStandardOrnTree.SmallAcaciaTree(false, woodBlock, woodMeta, leafBlock, leafMeta);
            }
        };

        public SmallAcaciaTree(boolean blockNotify, Block wood, int metaWood, Block leaves, int metaLeaves) {
            super(blockNotify, wood, metaWood, leaves, metaLeaves);
            this.layers = Arrays.asList(
                transform("T", WorldGenOrnamentalTree.LayerType.CORE),
                transform("XXXXTXXXX", WorldGenOrnamentalTree.LayerType.CORE),
                transform(" X XTX X ", WorldGenOrnamentalTree.LayerType.LEAF));
        }
    }

    public static class SmallJungleTree extends WorldGenStandardOrnTree {

        public static final OrnamentalTreeFactory FACTORY = new OrnamentalTreeFactory() {

            public WorldGenOrnamentalTree create(Block woodBlock, int woodMeta, Block leafBlock, int leafMeta) {
                return new WorldGenStandardOrnTree.SmallJungleTree(false, woodBlock, woodMeta, leafBlock, leafMeta);
            }
        };

        public SmallJungleTree(boolean blockNotify, Block wood, int metaWood, Block leaves, int metaLeaves) {
            super(blockNotify, wood, metaWood, leaves, metaLeaves);
        }

        protected void prepare(World world, Random rand, int x, int y, int z, int trunkHeight) {
            this.layers = new ArrayList();
            this.layers
                .add(transform(" 00  0XXX 0XTX  XXX      ", WorldGenOrnamentalTree.LayerType.CORE, rand.nextInt()));
            this.layers.add(transform(" X XTX X ", WorldGenOrnamentalTree.LayerType.CORE));
            this.layers.add(transform(" 00  0XXX 0XTX  XXX      ", WorldGenOrnamentalTree.LayerType.CORE));
            this.layers.add(transform("T", WorldGenOrnamentalTree.LayerType.LEAF));
        }
    }

    public static class SmallSpruceTree extends WorldGenStandardOrnTree {

        public static final OrnamentalTreeFactory FACTORY = new OrnamentalTreeFactory() {

            public WorldGenOrnamentalTree create(Block woodBlock, int woodMeta, Block leafBlock, int leafMeta) {
                return new WorldGenStandardOrnTree.SmallSpruceTree(false, woodBlock, woodMeta, leafBlock, leafMeta);
            }
        };

        public SmallSpruceTree(boolean blockNotify, Block wood, int metaWood, Block leaves, int metaLeaves) {
            super(blockNotify, wood, metaWood, leaves, metaLeaves);
            this.layers = Arrays.asList(
                transform("XXXXTXXXX", WorldGenOrnamentalTree.LayerType.CORE),
                transform(" X XTX X ", WorldGenOrnamentalTree.LayerType.CORE),
                transform(" X XTX X ", WorldGenOrnamentalTree.LayerType.LEAF),
                transform("T", WorldGenOrnamentalTree.LayerType.LEAF));
        }
    }

    public static class SmallOakTree extends WorldGenStandardOrnTree {

        public static final OrnamentalTreeFactory FACTORY = new OrnamentalTreeFactory() {

            public WorldGenOrnamentalTree create(Block woodBlock, int woodMeta, Block leafBlock, int leafMeta) {
                return new WorldGenStandardOrnTree.SmallOakTree(false, woodBlock, woodMeta, leafBlock, leafMeta);
            }
        };

        public SmallOakTree(boolean blockNotify, Block wood, int metaWood, Block leaves, int metaLeaves) {
            super(blockNotify, wood, metaWood, leaves, metaLeaves);
            this.layers = Arrays.asList(
                transform(" X XTX X ", WorldGenOrnamentalTree.LayerType.CORE),
                transform("XXXXTXXXX", WorldGenOrnamentalTree.LayerType.CORE),
                transform(" X XTX X ", WorldGenOrnamentalTree.LayerType.LEAF),
                transform("T", WorldGenOrnamentalTree.LayerType.LEAF));
        }
    }
}
