package org.jenkinsci.plugins.github.status.GitHubCommitStatusSetter

import org.apache.commons.collections.CollectionUtils
import org.jenkinsci.plugins.github.extension.status.StatusErrorHandler

import org.jenkinsci.plugins.github.status.source.ManuallyEnteredRepositorySource;

def f = namespace(lib.FormTagLib);

f.section(title: _('Report status to this repository and commit:')) {
    f.optionalBlock(
        name: 'reposSourceX',
        title: _('Repositories: '),
        checked: '${it.reposSource!=ManuallyEnteredRepositorySource}',
        field: 'repoSourceY',
        inline: 'true') {
        f.entry(
            title: "Repository:",
            field: "repoSource")
        {
            f.textbox()
        }
    }
    f.dropdownDescriptorSelector(title: _('Commit SHA: '), field: 'commitShaSource')
}

f.section(title: _('What:')) {
    f.dropdownDescriptorSelector(title: _('Commit context: '), field: 'contextSource')
    f.dropdownDescriptorSelector(title: _('Status result: '), field: 'statusResultSource')
    f.dropdownDescriptorSelector(title: _('Status backref: '), field: 'statusBackrefSource')
}

f.advanced {
    f.section(title: _('Advanced:')) {
        f.optionalBlock(
                checked: CollectionUtils.isNotEmpty(instance?.errorHandlers),
                inline: true,
                name: 'errorHandling',
                title: 'Handle errors') {
            f.block {
                f.hetero_list(items: CollectionUtils.isEmpty(instance?.errorHandlers)
                        ? []
                        : instance.errorHandlers,
                        addCaption: 'Add error handler',
                        name: 'errorHandlers',
                        oneEach: true, hasHeader: true, descriptors: StatusErrorHandler.all())
            }
        }
    }
}
