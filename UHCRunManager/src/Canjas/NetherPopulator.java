package Canjas;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

public class NetherPopulator extends BlockPopulator {

    public NetherPopulator() {}

    public Clipboard loadSchematic() {
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        System.out.println("Current absolute path is: " + s);
        File file = new File("/home/container/plugins/WorldEdit/schematics/nether.schem");
        ClipboardFormat format = ClipboardFormats.findByFile(file);
        Clipboard clipboard;
        try {
            ClipboardReader reader = format.getReader(new FileInputStream(file));
            clipboard = reader.read();
            return clipboard;
        } catch (IOException ignored) {
            System.out.println("CANT LOAD SCHEMATIC");
        }
        return null;
    }

    public void populate(World world, Random random, Chunk chunk)
    {
        for (int x = 0; x < 16; x++) {
            for (int y = 1; y < 128; y++) {
                for (int z = 0; z < 16; z++) {
                    Block block = chunk.getBlock(x, y, z);
                    int r = (int) (Math.random() * 1500000);
                    if (r == 3 && block.getType().compareTo(Material.STONE) == 0) {
                        Location loc = block.getLocation();
                        try {
                            Clipboard clipboard = loadSchematic();
                            com.sk89q.worldedit.world.World adaptedWorld = BukkitAdapter.adapt(Bukkit.getServer().getWorld("world"));
                            EditSession session = WorldEdit.getInstance().getEditSessionFactory().getEditSession(adaptedWorld, -1);
                            Operation operation = new ClipboardHolder(clipboard).createPaste(session)
                                    .to(BlockVector3.at(loc.getX(), 37, loc.getZ())).ignoreAirBlocks(false).build();
                            Operations.complete(operation);
                            session.flushSession();
                        } catch (ArrayIndexOutOfBoundsException | WorldEditException ignored) {}
                    }
                }
            }
        }
    }
}
