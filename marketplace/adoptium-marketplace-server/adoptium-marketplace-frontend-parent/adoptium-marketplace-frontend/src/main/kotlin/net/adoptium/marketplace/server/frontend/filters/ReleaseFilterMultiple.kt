package net.adoptium.marketplace.server.frontend.filters

import net.adoptium.marketplace.schema.Release
import net.adoptium.marketplace.schema.Vendor
import java.util.function.Predicate

class ReleaseFilterMultiple(
    private val featureVersion: List<Int>? = null,
    private val releaseName: List<String>? = null,
    private val vendor: List<Vendor>? = null,
    private val versionRange: List<VersionRangeFilter>? = null,
    private val lts: List<Boolean>? = null,
) : Predicate<Release> {
    override fun test(release: Release): Boolean {
        return (featureVersion == null || featureVersion.isEmpty() || featureVersion.contains(release.version_data.major)) &&
            (releaseName == null || releaseName.isEmpty() || releaseName.contains(release.release_name)) &&
            (vendor == null || vendor.isEmpty() || vendor.contains(release.vendor)) &&
            (versionRange == null || versionRange.isEmpty() || versionRange.any { it.test(release.version_data) }) &&
            (lts == null || lts.isEmpty() || lts.contains(release.version_data.isLts))
    }
}
