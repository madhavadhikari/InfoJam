package com.sevensemesterproject.infoJam.cloud;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;

public class CloudinaryResource {
	
	private static final Logger LOG = LoggerFactory.getLogger(CloudinaryResource.class);
	private Cloudinary cloudinary;

	public CloudinaryResource() {
		init();
	}
	private void init() {
		Map config = ObjectUtils.asMap("cloud_name", "texas-infojam", "api_key",
				"445794143242876", "api_secret", "r3P2f_F_HYAnMrQw1S1eBidVujI");
		Cloudinary cloudinary = new Cloudinary(config);
		this.cloudinary = cloudinary;
	}
	public String uploadFile(String file, String fileDirectory) throws IOException {
		Map uploadResult = this.cloudinary.uploader().upload(
				fileDirectory.concat(File.pathSeparator).concat(file),
				ObjectUtils.emptyMap());
		LOG.debug("Uploaded result {}", uploadResult);
		return null;
	}

	public String uploadFile(File file) throws IOException {
		Map uploadResult = this.cloudinary.uploader().upload(file,
				ObjectUtils.emptyMap());
		LOG.debug("Uploaded result {}", uploadResult);
		return null;
	}
	
	public String uploadFile(File file, String directory) throws IOException {
		Map params = ObjectUtils.asMap("public_id", directory, "overwrite", true,
				"resource_type", "image");
		Map uploadResult = this.cloudinary.uploader().upload(file, params);
		LOG.debug("Uploaded result {}", uploadResult);
		String publicId = uploadResult.get("public_id").toString().concat(".")
				.concat(uploadResult.get("format").toString());
		LOG.debug("Final file uploaded key {}", publicId);
		return publicId;
	}
	
	public String getFileUrl(String publicId) {
		if(publicId != null) {
			String url = cloudinary.url().secure(true)
					.transformation(new Transformation().width(250).height(168).crop("fit"))
					.generate(publicId);
			return url;
		}
		return "";
	}
	public void deleteFile(String publicId) throws IOException {
		Map uploader = cloudinary.uploader().destroy(publicId,
				ObjectUtils.asMap("invalidate", true));
		LOG.debug("Deleted response {}", uploader);
	}
}
