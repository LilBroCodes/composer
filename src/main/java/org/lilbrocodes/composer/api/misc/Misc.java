package org.lilbrocodes.composer.api.misc;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

/**
 * A utility class providing miscellaneous helper methods for 3D and 2D distance
 * calculations between entities and determining the current environment type
 * (client or server) in a Fabric modding context.
 */
public class Misc {

    /**
     * Calculates the Euclidean distance between two entities in 3D space.
     *
     * @param from The first entity (source).
     * @param to   The second entity (target).
     * @return The 3D distance between the two entities as a double.
     */
    public static double distanceTo3D(Entity from, Entity to) {
        Vec3d pos1 = from.getPos();
        Vec3d pos2 = to.getPos();
        double a = (pos1.x - pos2.x) * (pos1.x - pos2.x);
        double b = (pos1.y - pos2.y) * (pos1.y - pos2.y);
        double c = (pos1.z - pos2.z) * (pos1.z - pos2.z);
        return Math.sqrt(a + b + c);
    }

    /**
     * Calculates the Euclidean distance between two 3D vectors.
     * This method computes the distance between the given points in 3D space.
     *
     * @param from The first vector (source position).
     * @param to   The second vector (target position).
     * @return The 3D distance between the two vectors as a double.
     */
    public static double distanceTo3D(Vec3d from, Vec3d to) {
        double a = (from.x - to.x) * (from.x - to.x);
        double b = (from.y - to.y) * (from.y - to.y);
        double c = (from.z - to.z) * (from.z - to.z);
        return Math.sqrt(a + b + c);
    }

    /**
     * Calculates the Euclidean distance between two entities in 2D space
     * (ignoring the Y coordinate).
     *
     * @param from The first entity (source).
     * @param to   The second entity (target).
     * @return The 2D distance between the two entities as a double.
     */
    public static double distanceTo2D(Entity from, Entity to) {
        Vec3d pos1 = from.getPos();
        Vec3d pos2 = to.getPos();
        double a = (pos1.x - pos2.x) * (pos1.x - pos2.x);
        double b = (pos1.z - pos2.z) * (pos1.z - pos2.z);
        return Math.sqrt(a + b);
    }

    /**
     * Calculates the Euclidean distance between two 2D vectors, ignoring the Y coordinate.
     * This method computes the distance between the given points in 2D space, considering only
     * the X and Z axes.
     *
     * @param from The first vector (source position).
     * @param to   The second vector (target position).
     * @return The 2D distance between the two vectors as a double.
     */
    public static double distanceTo2D(Vec3d from, Vec3d to) {
        double a = (from.x - to.x) * (from.x - to.x);
        double b = (from.z - to.z) * (from.z - to.z);
        return Math.sqrt(a + b);
    }

    /**
     * Checks whether the current environment is the client.
     *
     * @return {@code true} if the current environment is the client, {@code false} otherwise.
     */
    public static boolean isClient() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
    }

    /**
     * Checks whether the current environment is the server.
     *
     * @return {@code true} if the current environment is the server, {@code false} otherwise.
     */
    public static boolean isServer() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER;
    }
}
