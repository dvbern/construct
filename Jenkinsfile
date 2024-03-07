@Library('dvbern-ci') _

properties([
	[$class: 'BuildDiscarderProperty', strategy: [$class: 'LogRotator', numToKeepStr: '10']],
	parameters([
		booleanParam(defaultValue: false, description: 'Do you want to perform a release?', name: 'performRelease'),
		string(defaultValue: '', description: 'This release version (optional)', name: 'releaseversion', trim: true),
		string(defaultValue: '', description: 'The next release version (optional)', name: 'nextreleaseversion', trim: true)
	])
])

def jdk = "Temurin_jdk-17.0.1+12"
// comma separated list of email addresses of all team members (for notification)
def recipients = "christoph.linder@dvbern.ch"

def masterBranchName = "master"
def developBranchName = "develop"
def featureBranchPrefix = "feature"

def nodeLabel = env.BRANCH_NAME.toString().startsWith(masterBranchName) ? 'oss-deploy-maven-central' : ''

if (params.performRelease) {
	// see https://issues.jenkins-ci.org/browse/JENKINS-53512
	def releaseVersion = params.releaseversion
	def nextReleaseVersion = params.nextreleaseversion

	dvbJGitFlowRelease {
		releaseversion = releaseVersion
		nextreleaseversion = nextReleaseVersion
		emailRecipients = recipients
		jdkVersion = jdk
		credentialsId = 'jenkins-github-token'
	}
} else {
	def handleFailures = {error ->
		if (branch.startsWith(featureBranchPrefix)) {
			// feature branche failures should only notify the feature owner
			step([
				$class                  : 'Mailer',
				notifyEveryUnstableBuild: true,
				recipients              : emailextrecipients([[$class: 'DevelopersRecipientProvider']]),
				sendToIndividuals       : true])

		} else {
			dvbErrorHandling.sendMail(recipients, currentBuild, error)
		}
	}

	try {
		node(nodeLabel) {
			stage('Checkout') {
				checkout([
					$class           : 'GitSCM',
					branches         : scm.branches,
					extensions       : scm.extensions + [[$class: 'LocalBranch', localBranch: '']],
					userRemoteConfigs: scm.userRemoteConfigs
				])
			}

			String branch = env.BRANCH_NAME.toString()
			currentBuild.displayName = "${branch}-${dvbMaven.pomVersion()}-${env.BUILD_NUMBER}"

			stage('Maven build and deploy') {
				// in develop and master branches attempt to deploy the artifacts, otherwise only run to the verify
				// phase.
				def branchSpecificGoal = {
					if (branch.startsWith(developBranchName) || branch.startsWith(masterBranchName)) {
						return "deploy"
					}

					return "verify"
				}

				withMaven(jdk: jdk) {
					dvbUtil.genericSh('./mvnw -B -U -Pdvbern.oss clean ' + branchSpecificGoal())
				}
				if (currentBuild.result == "UNSTABLE") {
					handleFailures("build is unstable")
				}
			}
		}
	} catch (Exception e) {
		currentBuild.result = "FAILURE"
		handleFailures(e)
		throw e
	}
}
